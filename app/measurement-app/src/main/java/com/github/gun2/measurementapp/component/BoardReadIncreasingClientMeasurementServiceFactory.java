package com.github.gun2.measurementapp.component;

import com.github.gun2.measurementapp.service.BoardApiService;
import com.github.gun2.measurementapp.service.BoardReadIncreasingClientMeasurementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 게시판 요청 성능을 측정하기 위한 서비스를 생성하는 팩토리 클래스
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BoardReadIncreasingClientMeasurementServiceFactory {
    private final BoardApiService boardApiService;
    public BoardReadIncreasingClientMeasurementService create(){
        return new BoardReadIncreasingClientMeasurementService(this.boardApiService);
    }
}
