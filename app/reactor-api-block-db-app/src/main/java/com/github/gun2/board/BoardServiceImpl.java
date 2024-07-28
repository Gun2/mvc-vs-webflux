package com.github.gun2.board;

import com.github.gun2.anycommon.board.BoardDto;
import com.github.gun2.anycommon.board.DtoJpaEntityModelMapper;
import com.github.gun2.boardjpacommon.board.BoardJpaEntity;
import com.github.gun2.boardjpacommon.board.BoardRepository;
import com.github.gun2.reactorcommon.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.stream.StreamSupport;

import static com.github.gun2.anycommon.board.DtoJpaEntityModelMapper.toDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    @Override
    public Mono<BoardDto> findById(Long id) {

        return Mono.just(toDto(boardRepository.findById(id).orElseThrow()));
    }

    @Override
    public Flux<BoardDto> findAll(){
        return Flux.fromStream(
                StreamSupport.stream(boardRepository.findAll().spliterator(), false).map(DtoJpaEntityModelMapper::toDto)
        );
    }
    @Override
    @Transactional
    public Mono<BoardDto> create(BoardDto.BoardRequest dto) {
        return Mono.just(toDto(boardRepository.save(
                BoardJpaEntity.builder()
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .createdAt(Instant.now())
                        .build()
        )));
    }
    @Override
    @Transactional
    public Mono<BoardDto> update(BoardDto.BoardRequest dto, Long id) {
        BoardJpaEntity board = boardRepository.findById(id).orElseThrow();
        return Mono.just(toDto(
                boardRepository.save(board.updateBoard(dto.getTitle(), dto.getContent()))
        ));
    }
    @Override
    public Mono<Void> delete(Long id) {
        boardRepository.deleteById(id);
        return Mono.empty();
    }
}
