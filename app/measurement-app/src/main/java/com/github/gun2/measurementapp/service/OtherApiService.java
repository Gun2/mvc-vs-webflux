package com.github.gun2.measurementapp.service;

import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class OtherApiService {

    private final CloseableHttpAsyncClient httpClient;
    public static final String SIMPLE_PATH = "/simples/reverse";
    public OtherApiService() {
        this.httpClient = HttpAsyncClients.createDefault();
        this.httpClient.start();
    }

    public Future<SimpleHttpResponse> simple(String targetUrl, String reverseStr, FutureCallback<SimpleHttpResponse> callback){
        SimpleHttpRequest request = SimpleRequestBuilder.get(targetUrl + SIMPLE_PATH + "/" + reverseStr).build();
        return this.httpClient.execute(request, callback);
    }
}
