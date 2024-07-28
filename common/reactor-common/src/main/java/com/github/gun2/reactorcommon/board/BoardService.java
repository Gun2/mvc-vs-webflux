package com.github.gun2.reactorcommon.board;

import com.github.gun2.anycommon.board.BoardDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
public interface BoardService {
    public Mono<BoardDto> findById(Long id);

    public Flux<BoardDto> findAll();
    public Mono<BoardDto> create(BoardDto.BoardRequest dto);
    public Mono<BoardDto> update(BoardDto.BoardRequest dto, Long id);
    public Mono<Void> delete(Long id);
}
