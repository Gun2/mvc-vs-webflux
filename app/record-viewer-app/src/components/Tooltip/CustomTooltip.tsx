import {
    //@ts-ignore
    TooltipPayload,
    TooltipProps
} from "recharts";
import {ChartData} from "../../../types/Chart";
import React, {useEffect, useState} from "react";

export const CustomTooltip = (
    {
        active, payload, label , ...rest
    }: TooltipProps<any, any>) => {
    if (active && payload && payload.length) {
        return (
            <div className="custom-tooltip">
                {payload.sort((v1, v2) => v2.value - v1.value).map( (value) => {
                    return (
                        <p style={{color: value.stroke}}>
                            {value.name} : <TooltipValue data={value}/>
                        </p>
                    )
                })}
                <p className="label">{label}</p>
            </div>
        );
    }
    return null;
};

type TooltipValueProps = {
   data : TooltipPayload["payload"]
}
const TooltipValue = (
    {
        data
    }: TooltipValueProps
) => {
    const payload = data.payload as ChartData;
    const [success, setSuccess] = useState(0);
    const [failure, setFailure] = useState(0);
    useEffect(() => {
        if (data.name === "multiThread"){
            setSuccess(payload.multiThreadSuccessCount);
            setFailure(payload.multiThreadFailureCount);
        } else if (data.name === "virtualMultiThread"){
            setSuccess(payload.virtualMultiThreadSuccessCount);
            setFailure(payload.virtualMultiThreadFailureCount);
        } else if (data.name === "reactorBlockDb"){
            setSuccess(payload.reactorBlockDbSuccessCount);
            setFailure(payload.reactorBlockDbFailureCount);
        } else if (data.name === "reactorNonBlockDb"){
            setSuccess(payload.reactorNonBlockDbSuccessCount);
            setFailure(payload.reactorNonBlockDbFailureCount);
        }
    }, [data])
    return (
        <>
            <text>{success}</text>
            {
                failure > 0 && (
                    <>
                        <text> / </text>
                        <text style={{color: "#f00"}}>{failure}</text>
                    </>
                )
            }
        </>
    )
}