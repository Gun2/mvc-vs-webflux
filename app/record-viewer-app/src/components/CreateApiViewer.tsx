import React from 'react';
import multiThreadCreateApiOutput from "../records/multi-thread-create-api-output.json";
import virtualMultiThreadCreateApiOutput from "../records/virtual-multi-thread-create-api-output.json";
import reactorCreateApiBlockDbOutput from "../records/reactor-create-api-block-db-output.json";
import reactorCreateApiNonBlockDbOutput from "../records/reactor-create-api-non-block-db-output.json";
import {getReadRecordData, RecordChartViewer} from "./RecordChartViewer";
import {RecordViewerLayout} from "./Layout/RecordViewerLayout";

export const CreateApiViewer = () => {
    return (
        <RecordViewerLayout
            title={"생성 API 성능 측정 결과"}
            description={"Db Create 쿼리를 수행하는 API의 성능 측정 결과입니다."}
            body={(
                <RecordChartViewer
                    data={getReadRecordData({
                        multiThreadOutput: multiThreadCreateApiOutput,
                        virtualMultiThreadOutput: virtualMultiThreadCreateApiOutput,
                        reactorBlockDbOutput: reactorCreateApiBlockDbOutput,
                        reactorNonBlockDbOutput: reactorCreateApiNonBlockDbOutput,
                        options: {
                            initClient: 100,
                            increasingClient: 100,
                            durationMsPerPhase: 5000,
                            phase: 10
                        }
                    })}
                />
            )}
        />
    );
};