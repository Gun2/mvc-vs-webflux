import React from 'react';
import multiThreadSimpleApiOutput from "../records/multi-thread-simple-api-output.json";
import virtualMultiThreadSimpleApiOutput from "../records/virtual-multi-thread-simple-api-output.json";
import reactorSimpleApiBlockDbOutput from "../records/reactor-simple-api-block-db-output.json";
import reactorSimpleApiNonBlockDbOutput from "../records/reactor-simple-api-non-block-db-output.json";
import {RecordChartViewer} from "./RecordChartViewer";
import {RecordViewerLayout} from "./Layout/RecordViewerLayout";

export const SimpleApiViewer = () => {
    return (
        <RecordViewerLayout
            title={"간단한 기능의 API 성능 측정 결과"}
            description={"부하가 거의 없는 간단한 동작을 수행하는 API의 성능 측정 결과입니다."}
            body={(
                <RecordChartViewer
                    data={{
                        multiThreadOutput: multiThreadSimpleApiOutput,
                        virtualMultiThreadOutput: virtualMultiThreadSimpleApiOutput,
                        reactorBlockDbOutput: reactorSimpleApiBlockDbOutput,
                        reactorNonBlockDbOutput: reactorSimpleApiNonBlockDbOutput,
                        options: {
                            initClient: 100,
                            increasingClient: 100,
                            durationMsPerPhase: 60000,
                            phase: 10
                        }
                    }}
                />
            )}
        />
    );
};