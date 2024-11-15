package com.github.gun2.measurementapp.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.concurrent.FutureCallback;

import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * 무거운 API 성능 측정 서비스
 */
@Slf4j
public class HeavyIncreasingClientMeasurementService extends TemplateIncreasingClientMeasurementService {
    private final UtilApiService utilApiService;
    private final int value;
    private final boolean threadSwitch;

    public HeavyIncreasingClientMeasurementService(UtilApiService utilApiService, TemplateIncreasingClientMeasurementConfig config, int value, boolean threadSwitch) {
        super(config);
        this.utilApiService = utilApiService;
        this.value = value;
        this.threadSwitch = threadSwitch;
    }

    @Override
    public Future<SimpleHttpResponse> request(Consumer callback) {
        return utilApiService.heavy(super.targetUrl, this.value, this.threadSwitch, new FutureCallback<SimpleHttpResponse>() {
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
