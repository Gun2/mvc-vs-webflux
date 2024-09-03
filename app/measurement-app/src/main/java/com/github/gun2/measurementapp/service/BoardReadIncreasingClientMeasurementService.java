package com.github.gun2.measurementapp.service;

import com.github.gun2.anycommon.board.BoardDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.concurrent.FutureCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 게시판 조회 API 성능 측정 서비스
 */
@Slf4j
public class BoardReadIncreasingClientMeasurementService extends TemplateIncreasingClientMeasurementService {
    private final BoardApiService boardApiService;

    public BoardReadIncreasingClientMeasurementService(BoardApiService boardApiService, TemplateIncreasingClientMeasurementConfig config) {
        super(config);
        this.boardApiService = boardApiService;
    }



    @Override
    public Future<SimpleHttpResponse> request(Runnable callback){
        return boardApiService.readById(super.targetUrl, 1, new FutureCallback<SimpleHttpResponse>() {
            @Override
            public void completed(SimpleHttpResponse simpleHttpResponse) {
                callback.run();
            }

            @Override
            public void failed(Exception e) {
                callback.run();
            }

            @Override
            public void cancelled() {
                callback.run();
            }
        });
    }


    private void warmUp(){
        for (int i = 0; i < 100; i++) {
            try {
                request(() -> {}).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
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
        try {
            boardApiService.create(super.targetUrl, getSample(), null).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        warmUp();
    }
}
