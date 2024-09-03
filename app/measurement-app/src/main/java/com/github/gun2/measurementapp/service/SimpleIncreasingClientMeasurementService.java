package com.github.gun2.measurementapp.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.concurrent.FutureCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * 게시판 조회 API 성능 측정 서비스
 */
@Slf4j
public class SimpleIncreasingClientMeasurementService extends TemplateIncreasingClientMeasurementService {
    private final OtherApiService otherApiService;

    public SimpleIncreasingClientMeasurementService(OtherApiService otherApiService, TemplateIncreasingClientMeasurementConfig config) {
        super(config);
        this.otherApiService = otherApiService;
    }

    @Override
    public Future<SimpleHttpResponse> request(Consumer callback) {
        return otherApiService.simple(super.targetUrl, "ABC", new FutureCallback<SimpleHttpResponse>() {
            @Override
            public void completed(SimpleHttpResponse simpleHttpResponse) {
                callback.accept(true);
            }

            @Override
            public void failed(Exception e) {
                log.error("request failed : {0}", e);
                callback.accept(false);
            }

            @Override
            public void cancelled() {
                callback.accept(false);
            }
        });
    }


    private void warmUp(){
        for (int i = 0; i < 100; i++) {
            try {
                request((isSucceed) -> {}).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void ready() {
        warmUp();
    }
}
