import React from 'react';
import multiThreadReadApiOutput from "../records/multi-thread-read-api-output.json";
import virtualMultiThreadReadApiOutput from "../records/virtual-multi-thread-read-api-output.json";
import reactorReadApiBlockDbOutput from "../records/reactor-read-api-block-db-output.json";
import reactorReadApiNonBlockDbOutput from "../records/reactor-read-api-non-block-db-output.json";
import {RecordChartViewer} from "./RecordChartViewer";
import {RecordViewerLayout} from "./Layout/RecordViewerLayout";

export const ReadApiViewer = () => {
    return (
        <RecordViewerLayout
            title={"조회 API 성능 측정 결과"}
            description={"Db Select 쿼리를 수행하는 API의 성능 측정 결과입니다."}
            body={(
                <RecordChartViewer
                    data={{
                        multiThreadOutput: multiThreadReadApiOutput,
                        virtualMultiThreadOutput: virtualMultiThreadReadApiOutput,
                        reactorBlockDbOutput: reactorReadApiBlockDbOutput,
                        reactorNonBlockDbOutput: reactorReadApiNonBlockDbOutput,
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