import React from 'react';
import multiThreadComplexApiOutput from "../../records/too-many-context-switches/multi-thread-heavy-api-output.json";
import virtualMultiThreadComplexApiOutput from "../../records/too-many-context-switches/virtual-multi-thread-heavy-api-output.json";
import reactorComplexApiBlockDbOutput from "../../records/too-many-context-switches/reactor-heavy-api-block-db-output.json";
import reactorComplexApiNonBlockDbOutput from "../../records/too-many-context-switches/reactor-heavy-api-non-block-db-output.json";
import {RecordChartViewer} from "./../RecordChartViewer";
import {RecordViewerLayout} from "./../Layout/RecordViewerLayout";

export const TooManyContextSwitchedApiViewer = () => {
    return (
        <RecordViewerLayout
            title={"많은 Context switch를 유발하는 API 성능 측정 결과"}
            description={"많은 Context switch를 유발하는 API의 성능 측정 결과입니다."}
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