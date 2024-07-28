package com.github.gun2.boardjpacommon.board;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Table(name = "board")
@Entity(name = "board")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Instant createdAt;

    public void updateContent(String content){
        this.content = content;
    }

    public BoardJpaEntity updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }
}
