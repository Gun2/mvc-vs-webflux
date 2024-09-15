import React from 'react';

export type RecordViewerLayoutProps = {
    title: React.ReactNode,
    description: React.ReactNode,
    body: React.ReactNode
}
export const RecordViewerLayout = (
    {
        title,
        body,
        description
    }: RecordViewerLayoutProps
) => {
    return (
        <div style={{display: "flex", flexDirection: "column", gap: 5, margin: 20}}>
            <div style={{fontSize:24, fontWeight: "bold"}}>
                {title}
            </div>
            <div style={{color: "gray"}}>
                {description}
            </div>
            <div style={{width: "100%", height: "60vh"}}>
                {body}
            </div>
        </div>
    );
};