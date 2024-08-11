package com.github.gun2.measurementapp.component;

import com.github.gun2.measurementapp.properties.AppInfo;
import com.github.gun2.measurementapp.service.BoardApiService;
import com.github.gun2.measurementapp.service.BoardCreateIncreasingClientMeasurementService;
import com.github.gun2.measurementapp.service.BoardReadIncreasingClientMeasurementService;
import com.github.gun2.measurementapp.service.TemplateIncreasingClientMeasurementConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 게시판 요청 성능을 측정하기 위한 서비스를 생성하는 팩토리 클래스
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BoardApiIncreasingClientMeasurementServiceFactory {
    private final BoardApiService boardApiService;
    private final AppInfo appInfo;

    /**
     * 조회 API 성능 측정 서비스 생성
     * @return
     */
    public BoardReadIncreasingClientMeasurementService createReadApiMeasurementService(){
        return createReadApiMeasurementService(convert(appInfo.getReadMeasurementConfig()));
    }

    public BoardReadIncreasingClientMeasurementService createReadApiMeasurementService(TemplateIncreasingClientMeasurementConfig config){
        return new BoardReadIncreasingClientMeasurementService(this.boardApiService, config);
    }

    /**
     * 생성 API 성능 측정 서비스 생성
     * @return
     */
    public BoardCreateIncreasingClientMeasurementService createCreateApiMeasurementService(){
        return new BoardCreateIncreasingClientMeasurementService(this.boardApiService, convert(appInfo.getCreateMeasurementConfig()));
    }

    public TemplateIncreasingClientMeasurementConfig convert(AppInfo.MeasurementConfig measurementConfig){
        return TemplateIncreasingClientMeasurementConfig.builder()
                .phase(measurementConfig.getPhase())
                .initClient(measurementConfig.getInitClient())
                .increasingClient(measurementConfig.getIncreasingClient())
                .outputPath(measurementConfig.getOutputPath())
                .durationMsPerPhase(measurementConfig.getDurationMsPerPhase())
                .build();
    }
}
