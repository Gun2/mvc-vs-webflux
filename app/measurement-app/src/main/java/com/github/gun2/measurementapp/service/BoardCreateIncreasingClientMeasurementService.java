package com.github.gun2.measurementapp.service;

import com.github.gun2.anycommon.board.BoardDto;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 게시판 생성 API 성능 측정 서비스
 */
@Slf4j
public class BoardCreateIncreasingClientMeasurementService extends TemplateIncreasingClientMeasurementService {
    private final BoardApiService boardApiService;
    private final BoardDto.BoardRequest sample = getSample();

    public BoardCreateIncreasingClientMeasurementService(BoardApiService boardApiService, TemplateIncreasingClientMeasurementConfig config) {
        super(config);
        this.boardApiService = boardApiService;
    }

    @Override
    public Mono<BoardDto> request(){
        return boardApiService.create(sample);
    }


    private void warmUp(){
        for (int i = 0; i < 100; i++) {
            request().block();
        }
    }


    private BoardDto.BoardRequest getSample() {
        return BoardDto.BoardRequest.builder()
                .title("test title")
                .content("test content")
                .build();
    }

    @Override
    public void ready() {
        warmUp();
    }
}
