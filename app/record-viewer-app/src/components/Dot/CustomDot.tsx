import {Dot, DotProps} from "recharts";
import React from "react";
import {ChartData} from "../../../types/Chart";

export const MultiThreadCustomDot = (
    {
        payload,
        ...rest
    }: CustomDotProps
) => {
    return <CustomDot {...rest} payload={payload} error={payload && payload?.multiThreadFailureCount > 0}/>
}

export const VirtualMultiThreadCustomDot = (
    {
        payload,
        ...rest
    }: CustomDotProps
) => {
    return <CustomDot {...rest} payload={payload} error={payload && payload?.virtualMultiThreadFailureCount > 0}/>
}

export const ReactorBlockDbCustomDot = (
    {
        payload,
        ...rest
    }: CustomDotProps
) => {
    return <CustomDot {...rest} payload={payload} error={payload && payload?.reactorBlockDbFailureCount > 0}/>
}

export const ReactorNonBlockDbCustomDot = (
    {
        payload,
        ...rest
    }: CustomDotProps
) => {
    return <CustomDot {...rest} payload={payload} error={payload && payload?.reactorNonBlockDbFailureCount > 0}/>
}

export type CustomDotProps = DotProps & {payload ?: ChartData, error ?: boolean}

export const CustomDot = (
    {
        error,
        ...rest
    }: CustomDotProps
) => {
    return <Dot {...rest} r={5} fill={error ? "#f00" : rest.fill}/>
}