package com.github.gun2.multithreadcommon.board;

import com.github.gun2.anycommon.board.BoardDto;
import com.github.gun2.boardjpacommon.board.BoardJpaEntity;
import com.github.gun2.boardjpacommon.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static com.github.gun2.anycommon.board.DtoJpaEntityModelMapper.toDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public BoardDto findById(Long id) {
        return toDto(boardRepository.findById(id).orElseThrow());
    }

    @Transactional
    public BoardDto create(BoardDto.BoardRequest dto) {
        return toDto(boardRepository.save(
                BoardJpaEntity.builder()
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .createdAt(Instant.now())
                        .build()
        ));
    }

    @Transactional
    public BoardDto update(BoardDto.BoardRequest dto, Long id) {
        BoardJpaEntity board = boardRepository.findById(id).orElseThrow();
        return toDto(boardRepository.save(board.updateBoard(dto.getTitle(), dto.getContent())));
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
