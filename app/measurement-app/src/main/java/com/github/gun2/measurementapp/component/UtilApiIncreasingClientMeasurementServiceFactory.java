package com.github.gun2.measurementapp.component;

import com.github.gun2.measurementapp.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Util API의 요청 성능을 측정하기 위한 서비스를 생성하는 팩토리 클래스
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UtilApiIncreasingClientMeasurementServiceFactory {
    private final UtilApiService utilApiService;

    /**
     * 가벼운 기능의 API 성능 측정 서비스 생성
     * @return
     */
    public SimpleIncreasingClientMeasurementService createSimpleApiMeasurementService(TemplateIncreasingClientMeasurementConfig config){
        return new SimpleIncreasingClientMeasurementService(this.utilApiService, config);
    }

    /**
     * Block이 포함된 API 성능 측정 서비스 생성
     * @return
     */
    public BlockIncreasingClientMeasurementService createBlockApiMeasurementService(TemplateIncreasingClientMeasurementConfig config){
        return new BlockIncreasingClientMeasurementService(this.utilApiService, config);
    }

    /**
     * 무거운 기능의 API 성능 측정 서비스 생성
     * @param config
     * @return
     */
    public HeavyIncreasingClientMeasurementService createHeavyApiMeasurementService(TemplateIncreasingClientMeasurementConfig config){
        return new HeavyIncreasingClientMeasurementService(this.utilApiService, config);
    }
}
