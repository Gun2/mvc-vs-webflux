package com.github.gun2.measurementapp.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.concurrent.FutureCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * 무거운 API 성능 측정 서비스
 */
@Slf4j
public class ComplexIncreasingClientMeasurementService extends TemplateIncreasingClientMeasurementService {
    private final UtilApiService utilApiService;

    public ComplexIncreasingClientMeasurementService(UtilApiService utilApiService, TemplateIncreasingClientMeasurementConfig config) {
        super(config);
        this.utilApiService = utilApiService;
    }

    @Override
    public Future<SimpleHttpResponse> request(Consumer callback) {
        return utilApiService.complex(super.targetUrl, 1000, new FutureCallback<SimpleHttpResponse>() {
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

    @Override
    public void ready() {
    }
}
