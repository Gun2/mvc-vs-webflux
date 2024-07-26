package com.github.gun2.multithreadcommon.board;

import lombok.*;

import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardDataEntity {

    private Long id;

    private String title;

    private String content;

    private Instant createdAt;

    public void updateContent(String content){
        this.content = content;
    }

    public BoardDataEntity updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }
}
