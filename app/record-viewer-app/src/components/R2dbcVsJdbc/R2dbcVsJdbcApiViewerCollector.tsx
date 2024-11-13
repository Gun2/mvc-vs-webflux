import React from 'react';
import {R2dbcVsJdbcReadApiWithCloseDbViewer} from "./R2dbcVsJdbcReadApiWithCloseDbViewer";
import {R2dbcVsJdbcReadApiWithFarDbViewer} from "./R2dbcVsJdbcReadApiWithFarDbViewer";
import {R2dbcVsJdbcCreateApiWithCloseDbViewer} from "./R2dbcVsJdbcCreateApiWithCloseDbViewer";
import {R2dbcVsJdbcCreateApiWithFarDbViewer} from "./R2dbcVsJdbcCreateApiWithFarDbViewer";

export const R2dbcVsJdbcApiViewerCollector = () => {
    return (
        <div style={{display: "flex", flexDirection: "column", gap: 50}}>
            <Row>
                <Col>
                    <R2dbcVsJdbcReadApiWithCloseDbViewer/>
                </Col>
                <Col>
                    <R2dbcVsJdbcReadApiWithFarDbViewer/>
                </Col>
            </Row>
            <Row>
                <Col>
                    <R2dbcVsJdbcCreateApiWithCloseDbViewer/>
                </Col>
                <Col>
                    <R2dbcVsJdbcCreateApiWithFarDbViewer/>
                </Col>
            </Row>
        </div>

    );
};

const Row = (
    {
        children
    } : {
        children : React.ReactNode
    }
) => <div style={{display: "flex"}} children={children}/>
const Col = (
    {
        children
    } : {
        children : React.ReactNode
    }
) => <div style={{flex:1}} children={children}/>