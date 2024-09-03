package com.github.gun2.measurementapp.service;

import com.github.gun2.anycommon.board.BoardDto;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.ContentType;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class BoardApiService {

    private final CloseableHttpAsyncClient httpClient;
    public static final String CREATE_PATH = "/boards";
    public static final String READ_PATH = "/boards";

    public BoardApiService() {
        this.httpClient = HttpAsyncClients.createDefault();
        this.httpClient.start();
    }

    public Future<SimpleHttpResponse> create(String targetUrl, BoardDto.BoardRequest boardRequest, FutureCallback<SimpleHttpResponse> callback) {
        SimpleHttpRequest request = SimpleRequestBuilder.post(targetUrl + CREATE_PATH)
                .setBody("""
                        {
                        "title": "%s",
                        "content": "%s"
                        }
                        """.formatted(boardRequest.getTitle(), boardRequest.getContent()), ContentType.APPLICATION_JSON).build();
        return this.httpClient.execute(request, callback);
    }

    public Future<SimpleHttpResponse> readById(String targetUrl, Integer id, FutureCallback<SimpleHttpResponse> callback){
        SimpleHttpRequest request = SimpleRequestBuilder.get(targetUrl + READ_PATH + "/" + id).build();

        return this.httpClient.execute(request, callback);
    }
}
