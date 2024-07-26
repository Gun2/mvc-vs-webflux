package com.github.gun2.anycommon.board;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private Instant createdAt;
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class BoardRequest {
        @NotBlank
        private String title;
        private String content;
    }
}
