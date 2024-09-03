package com.github.gun2.measurementapp.component;

import com.github.gun2.measurementapp.service.OtherApiService;
import com.github.gun2.measurementapp.service.SimpleIncreasingClientMeasurementService;
import com.github.gun2.measurementapp.service.TemplateIncreasingClientMeasurementConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 기타 API의 요청 성능을 측정하기 위한 서비스를 생성하는 팩토리 클래스
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OtherApiIncreasingClientMeasurementServiceFactory {
    private final OtherApiService otherApiService;

    /**
     * 가벼운 기능의 API 성능 측정 서비스 생성
     * @return
     */
    public SimpleIncreasingClientMeasurementService createSimpleApiMeasurementService(TemplateIncreasingClientMeasurementConfig config){
        return new SimpleIncreasingClientMeasurementService(this.otherApiService, config);
    }
}
