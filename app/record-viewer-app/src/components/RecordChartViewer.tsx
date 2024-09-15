import React from 'react';
import {ComposedChart, Legend, Line, ResponsiveContainer, Tooltip, XAxis, YAxis} from "recharts";
import {ChartData} from "../../types/Chart";
import {
    MultiThreadCustomDot,
    ReactorBlockDbCustomDot,
    ReactorNonBlockDbCustomDot,
    VirtualMultiThreadCustomDot
} from "./Dot/CustomDot";
import {CustomTooltip} from "./Tooltip/CustomTooltip";
import {Record} from "../../types/Record";


const COLORS = {
    MULTI_THREAD_COLOR : "#339fff",
    VIRTUAL_MULTI_THREAD_COLOR: "#6335ea",
    REACTOR_BLOCK_DB_COLOR: "#bd35ea",
    REACTOR_NON_BLOCK_DB_COLOR: "#eab735",
}

export type RecordViewerProps = {
    data : ChartData[]
}

export const RecordChartViewer = (
    {
        data
    }: RecordViewerProps
) => {
    return (
        <ResponsiveContainer>
            <ComposedChart
                width={1600}
                height={800}
                data={data}
            >
                <XAxis dataKey="name" scale="auto"/>
                <YAxis/>
                <Line
                    type="monotone"
                    dataKey={"multiThreadSuccessCount"}
                    stroke={COLORS.MULTI_THREAD_COLOR}
                    dot={<MultiThreadCustomDot/>}
                    name={"multiThread"}
                />
                <Line
                    type="monotone"
                    dataKey={"virtualMultiThreadSuccessCount"}
                    stroke={COLORS.VIRTUAL_MULTI_THREAD_COLOR}
                    dot={<VirtualMultiThreadCustomDot/>}
                    name={"virtualMultiThread"}
                />
                <Line
                    type="monotone"
                    dataKey={"reactorBlockDbSuccessCount"}
                    stroke={COLORS.REACTOR_BLOCK_DB_COLOR}
                    dot={<ReactorBlockDbCustomDot/>}
                    name={"reactorBlockDb"}
                />
                <Line
                    type="monotone"
                    dataKey={"reactorNonBlockDbSuccessCount"}
                    stroke={COLORS.REACTOR_NON_BLOCK_DB_COLOR}
                    dot={<ReactorNonBlockDbCustomDot/>}
                    name={"reactorNonBlockDb"}

                />
                <Tooltip content={<CustomTooltip/>}/>
                <Legend/>
            </ComposedChart>
        </ResponsiveContainer>
    );
};



type GetReadRecordDataArgs = {
    multiThreadOutput: Record[]
    virtualMultiThreadOutput: Record[]
    reactorBlockDbOutput: Record[]
    reactorNonBlockDbOutput: Record[]
    options: {
        initClient: number
        increasingClient: number
        durationMsPerPhase: number
        phase: number
    }
}
export function getReadRecordData(
    {
        multiThreadOutput,
        virtualMultiThreadOutput,
        reactorBlockDbOutput,
        reactorNonBlockDbOutput,
        options
    }: GetReadRecordDataArgs
): ChartData[]{
    const equalsSize = [multiThreadOutput, virtualMultiThreadOutput, reactorBlockDbOutput, reactorNonBlockDbOutput].every(output => output.length === options.phase);
    if (!equalsSize){
        throw new Error("측정 단계가 서로 다릅니다.");
    }
    return [...new Array(options.phase)].map((value, index, array) => ({
        multiThreadSuccessCount : multiThreadOutput[index].successCount,
        multiThreadFailureCount : multiThreadOutput[index].failureCount,
        virtualMultiThreadSuccessCount: virtualMultiThreadOutput[index].successCount,
        virtualMultiThreadFailureCount: virtualMultiThreadOutput[index].failureCount,
        reactorBlockDbSuccessCount: reactorBlockDbOutput[index].successCount,
        reactorBlockDbFailureCount: reactorBlockDbOutput[index].failureCount,
        reactorNonBlockDbSuccessCount: reactorNonBlockDbOutput[index].successCount,
        reactorNonBlockDbFailureCount: reactorNonBlockDbOutput[index].failureCount,
        name: `${(index + 1) * options.durationMsPerPhase / 1000}s (${options.initClient + (index * options.increasingClient)} users)`,
    }))
}