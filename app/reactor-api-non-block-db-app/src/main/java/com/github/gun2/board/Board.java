package com.github.gun2.board;

import lombok.*;

import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    private Long id;

    private String title;

    private String content;

    private Instant createdAt;

    public void updateContent(String content){
        this.content = content;
    }

    public Board updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }
}
