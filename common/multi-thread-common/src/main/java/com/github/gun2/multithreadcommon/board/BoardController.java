package com.github.gun2.multithreadcommon.board;

import com.github.gun2.anycommon.board.BoardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/{id}")
    public BoardDto readById(
            @PathVariable("id")Long id
            ){
        return boardService.findById(id);
    }

    @PostMapping
    public BoardDto create(
        @Validated @RequestBody BoardDto.BoardRequest dto
    ){
        return boardService.create(dto);
    }

    @PutMapping("/{id}")
    public BoardDto update(
            @PathVariable("id")Long id,
            @Validated @RequestBody BoardDto.BoardRequest dto
    ){
        return boardService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(
            @PathVariable("id")Long id
    ){
        boardService.delete(id);
        return true;
    }
}
