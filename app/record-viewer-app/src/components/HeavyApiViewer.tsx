import React from 'react';
import multiThreadComplexApiOutput from "../records/multi-thread-heavy-api-output.json";
import virtualMultiThreadComplexApiOutput from "../records/virtual-multi-thread-heavy-api-output.json";
import reactorComplexApiBlockDbOutput from "../records/reactor-heavy-api-block-db-output.json";
import reactorComplexApiNonBlockDbOutput from "../records/reactor-heavy-api-non-block-db-output.json";
import {RecordChartViewer} from "./RecordChartViewer";
import {RecordViewerLayout} from "./Layout/RecordViewerLayout";

export const HeavyApiViewer = () => {
    return (
        <RecordViewerLayout
            title={"무거운 기능의 API 성능 측정 결과"}
            description={"많은 CPU 처리량을 요구하는 API의 성능 측정 결과입니다."}
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