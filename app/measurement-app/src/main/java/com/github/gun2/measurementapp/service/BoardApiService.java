package com.github.gun2.measurementapp.service;

import com.github.gun2.anycommon.board.BoardDto;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Service
public class BoardApiService {

    private final String targetUrl;
    private final WebClient webClient;
    public static final String CREATE_PATH = "/boards";
    public static final String READ_PATH = "/boards";

    public BoardApiService(Environment environment) {
        this.targetUrl = environment.getProperty("app.target.url");
        this.webClient = WebClient.builder()
                .baseUrl(this.targetUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<BoardDto> create(BoardDto.BoardRequest boardRequest){
        return this.webClient.post().uri(CREATE_PATH)
                .bodyValue(boardRequest)
                .retrieve()
                .bodyToMono(BoardDto.class);
    }

    public Mono<BoardDto> readById(Integer id){
        return this.webClient.get().uri(READ_PATH + "/" + id)
                .retrieve()
                .bodyToMono(BoardDto.class);
    }
}
