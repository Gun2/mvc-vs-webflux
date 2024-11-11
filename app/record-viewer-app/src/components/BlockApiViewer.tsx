import React from 'react';
import multiThreadComplexApiOutput from "../records/multi-thread-block-api-output.json";
import virtualMultiThreadComplexApiOutput from "../records/virtual-multi-thread-block-api-output.json";
import reactorComplexApiBlockDbOutput from "../records/reactor-block-api-block-db-output.json";
import reactorComplexApiNonBlockDbOutput from "../records/reactor-block-api-non-block-db-output.json";
import {RecordChartViewer} from "./RecordChartViewer";
import {RecordViewerLayout} from "./Layout/RecordViewerLayout";

export const BlockApiViewer = () => {
    return (
        <RecordViewerLayout
            title={"블로킹 작업이 포함된 API 성능 측정 결과"}
            description={"많은 블럭킹을 동반한 동작을 수행하는 API의 성능 측정 결과입니다."}
            body={(
                <RecordChartViewer
                    data={{
                        multiThreadOutput: multiThreadComplexApiOutput,
                        virtualMultiThreadOutput: virtualMultiThreadComplexApiOutput,
                        reactorBlockDbOutput: reactorComplexApiBlockDbOutput,
                        reactorNonBlockDbOutput: reactorComplexApiNonBlockDbOutput,
                        options: {
                            initClient: 50,
                            increasingClient: 50,
                            durationMsPerPhase: 10000,
                            phase: 10
                        }
                    }}
                />
            )}
        />
    );
};