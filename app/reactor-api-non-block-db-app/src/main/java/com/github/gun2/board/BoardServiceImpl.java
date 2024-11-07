package com.github.gun2.board;

import com.github.gun2.anycommon.board.BoardDto;
import com.github.gun2.reactorcommon.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    @Override
    public Mono<BoardDto> findById(Long id) {

        return boardRepository.findById(id).map(DtoToDataModelMapper::toDto);
    }

    @Override
    public Flux<BoardDto> findAll(){
        return boardRepository.findAll().map(DtoToDataModelMapper::toDto);
    }

    @Override
    public Mono<BoardDto> create(BoardDto.BoardRequest dto) {
        return boardRepository.save(
                Board.builder()
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .createdAt(LocalDateTime.now())
                        .build()
        ).map(DtoToDataModelMapper::toDto);
    }

    @Override
    public Mono<BoardDto> update(BoardDto.BoardRequest dto, Long id) {
        return boardRepository.findById(id).map(board -> {
            board.updateBoard(dto.getTitle(), dto.getContent());
            return boardRepository.save(board).map(DtoToDataModelMapper::toDto);
        }).flatMap(boardDtoMono -> boardDtoMono);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return boardRepository.deleteById(id);
    }
}
