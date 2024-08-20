package com.github.gun2.measurementapp.service;

import com.github.gun2.anycommon.board.BoardDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Service
public class BoardApiService {

    private final WebClient webClient;
    public static final String CREATE_PATH = "/boards";
    public static final String READ_PATH = "/boards";

    public BoardApiService() {
        ConnectionProvider connectionProvider = ConnectionProvider.builder("web-client")
                .maxConnections(Integer.MAX_VALUE)
        .pendingAcquireMaxCount(-1)
        .build();
        ReactorClientHttpConnector clientHttpConnector = new ReactorClientHttpConnector(HttpClient.create(connectionProvider));
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(clientHttpConnector)
                .build();
    }

    public Mono<BoardDto> create(String targetUrl, BoardDto.BoardRequest boardRequest){
        return this.webClient.post().uri(targetUrl + CREATE_PATH)
                .bodyValue(boardRequest)
                .retrieve()
                .bodyToMono(BoardDto.class);
    }

    public Mono<BoardDto> readById(String targetUrl, Integer id){
        return this.webClient.get().uri(targetUrl + READ_PATH + "/" + id)
                .retrieve()
                .bodyToMono(BoardDto.class);
    }
}
