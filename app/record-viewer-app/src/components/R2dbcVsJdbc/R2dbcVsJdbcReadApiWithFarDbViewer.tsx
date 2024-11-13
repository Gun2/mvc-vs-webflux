import React from 'react';
import reactorSimpleApiBlockDbOutput from "../../records/r2dbc-vs-jdbc/far/reactor-read-api-block-db-output.json";
import reactorSimpleApiNonBlockDbOutput from "../../records/r2dbc-vs-jdbc/far/reactor-read-api-non-block-db-output.json";
import {RecordViewerLayout} from "../Layout/RecordViewerLayout";
import {R2dbcVsJdbcChartViewer} from "./R2dbcVsJdbcChartViewer";

/**
 * 멀은 거리의 DB와 연결된 조회 api 성능 결과
 * @constructor
 */
export const R2dbcVsJdbcReadApiWithFarDbViewer = () => {
    return (
        <RecordViewerLayout
            title={"조회 API(먼 거리의 DB)"}
            description={"긴 네트워크 연결 시간으로 인해 DB 호출에 많은 블로킹시간이 소요되는 환경입니다."}
            body={(
                <R2dbcVsJdbcChartViewer
                    data={{
                        reactorBlockDbOutput: reactorSimpleApiBlockDbOutput,
                        reactorNonBlockDbOutput: reactorSimpleApiNonBlockDbOutput,
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