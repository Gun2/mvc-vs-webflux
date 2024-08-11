package com.github.gun2.measurementapp.service;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateIncreasingClientMeasurementConfig {
    /**
     * 대상 서버 url
     */
    private String targetUrl;
    /**
     * 초기 요청 클라이언트 수
     */
    private int initClient = 1;
    /**
     * 증가될 클라이언트 수
     */
    private int increasingClient = 10;
    /**
     * 증가 단계 별 지속 시간
     */
    private int durationMsPerPhase = 5000;
    /**
     * 총 단계
     */
    private int phase = 10;
    /**
     * 측정 결과 저장 경로
     */
    private String outputPath = "output.json";
}
