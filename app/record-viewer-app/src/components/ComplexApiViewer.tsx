import React from 'react';
import multiThreadComplexApiOutput from "../records/multi-thread-complex-api-output.json";
import virtualMultiThreadComplexApiOutput from "../records/virtual-multi-thread-complex-api-output.json";
import reactorComplexApiBlockDbOutput from "../records/reactor-complex-api-block-db-output.json";
import reactorComplexApiNonBlockDbOutput from "../records/reactor-complex-api-non-block-db-output.json";
import {RecordChartViewer} from "./RecordChartViewer";
import {RecordViewerLayout} from "./Layout/RecordViewerLayout";

export const ComplexApiViewer = () => {
    return (
        <RecordViewerLayout
            title={"복잡한 기능의 API 성능 측정 결과"}
            description={"많은 블럭킹을 동반한 복잡한 동작을 수행하는 API의 성능 측정 결과입니다."}
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