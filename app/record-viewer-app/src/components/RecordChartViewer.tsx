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
    data : RecordData
}

export const RecordChartViewer = (
    {
        data
    }: RecordViewerProps
) => {
    return (
        <>
            <div style={{margin: 5}}>
                <div>
                    최초 사용자 수 : {data.options.initClient} users
                </div>
                <div>
                    매 단계 별 증가 되는 사용자 수 : {data.options.increasingClient} users
                </div>
                <div>
                    총 단계 : {data.options.phase}
                </div>
                <div>
                    단계 지속 시간 : {data.options.durationMsPerPhase} ms
                </div>
            </div>
            <ResponsiveContainer>
                <ComposedChart
                    width={1600}
                    height={800}
                    data={getChartData(data)}
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
        </>
    );
};

type RecordGroup = {
    multiThreadOutput: Record[]
    virtualMultiThreadOutput: Record[]
    reactorBlockDbOutput: Record[]
    reactorNonBlockDbOutput: Record[]
}

type RecordData = RecordGroup & {
    options: {
        initClient: number
        increasingClient: number
        durationMsPerPhase: number
        phase: number
    }
}
export function getChartData(
    {
        multiThreadOutput,
        virtualMultiThreadOutput,
        reactorBlockDbOutput,
        reactorNonBlockDbOutput,
        options
    }: RecordData
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