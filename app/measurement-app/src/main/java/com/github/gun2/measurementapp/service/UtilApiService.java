package com.github.gun2.measurementapp.service;

import com.github.gun2.measurementapp.util.MeasurementHttpClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
@Slf4j
public class UtilApiService {

    private final CloseableHttpAsyncClient httpClient;
    public static final String SIMPLE_PATH = "/utils/reverse";
    public static final String BLOCK_PATH = "/utils/sleep";
    public UtilApiService() {
        this.httpClient = MeasurementHttpClientFactory.create();
        this.httpClient.start();
    }

    /**
     * 문자열 뒤집기 API
     * (간단한 기능 API)
     * @param targetUrl API 서버 URL
     * @param reverseStr 뒤집을 문자열
     * @param callback
     * @return
     */
    public Future<SimpleHttpResponse> simple(String targetUrl, String reverseStr, FutureCallback<SimpleHttpResponse> callback){
        SimpleHttpRequest request = SimpleRequestBuilder.get(targetUrl + SIMPLE_PATH + "/" + reverseStr).build();
        return this.httpClient.execute(request, callback);
    }


    /**
     * sleep API
     * (복잡한 기능 API)
     * @param targetUrl API 서버 URL
     * @param time sleep time (ms)
     * @param callback
     * @return
     */
    public Future<SimpleHttpResponse> block(String targetUrl, Integer time, FutureCallback<SimpleHttpResponse> callback){
        SimpleHttpRequest request = SimpleRequestBuilder.get(targetUrl + BLOCK_PATH + "/" + time).build();

        return this.httpClient.execute(request, callback);
    }
}
