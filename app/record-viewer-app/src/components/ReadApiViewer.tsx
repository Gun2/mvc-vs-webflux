import React from 'react';
import {ComposedChart, Legend, Line, ResponsiveContainer, Tooltip, XAxis, YAxis} from "recharts";
import multiThreadReadApiOutput from "../records/multi-thread-read-api-output.json";
import virtualMultiThreadReadApiOutput from "../records/virtual-multi-thread-read-api-output.json";
import reactorReadApiBlockDbOutput from "../records/reactor-read-api-block-db-output.json";
import reactorReadApiNonBlockDbOutput from "../records/reactor-read-api-non-block-db-output.json";
import {ChartData} from "../../types/Chart";
import {
    MultiThreadCustomDot,
    ReactorBlockDbCustomDot,
    ReactorNonBlockDbCustomDot,
    VirtualMultiThreadCustomDot
} from "./Dot/CustomDot";
import {CustomTooltip} from "./Tooltip/CustomTooltip";


const COLORS = {
    MULTI_THREAD_COLOR : "#339fff",
    VIRTUAL_MULTI_THREAD_COLOR: "#6335ea",
    REACTOR_BLOCK_DB_COLOR: "#bd35ea",
    REACTOR_NON_BLOCK_DB_COLOR: "#eab735",
}

const data = getReadRecordData();
export const ReadApiViewer = () => {
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



function getReadRecordData(): ChartData[]{
    const equalsSize = [virtualMultiThreadReadApiOutput, reactorReadApiBlockDbOutput, reactorReadApiNonBlockDbOutput].every(output => output.length === multiThreadReadApiOutput.length);
    if (!equalsSize){
        throw new Error("측정 단계가 서로 다릅니다.");
    }
    return [...new Array(multiThreadReadApiOutput.length)].map((value, index, array) => ({
        multiThreadSuccessCount : multiThreadReadApiOutput[index].successCount,
        multiThreadFailureCount : multiThreadReadApiOutput[index].failureCount,
        virtualMultiThreadSuccessCount: virtualMultiThreadReadApiOutput[index].successCount,
        virtualMultiThreadFailureCount: virtualMultiThreadReadApiOutput[index].failureCount,
        reactorBlockDbSuccessCount: reactorReadApiBlockDbOutput[index].successCount,
        reactorBlockDbFailureCount: reactorReadApiBlockDbOutput[index].failureCount,
        reactorNonBlockDbSuccessCount: reactorReadApiNonBlockDbOutput[index].successCount,
        reactorNonBlockDbFailureCount: reactorReadApiNonBlockDbOutput[index].failureCount,
        name: `${(index + 1) * 5}s (${(index + 1) * 100} users)`,
    }))
}