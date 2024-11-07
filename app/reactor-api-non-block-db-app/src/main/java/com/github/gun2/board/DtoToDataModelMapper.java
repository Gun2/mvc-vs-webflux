package com.github.gun2.board;

import com.github.gun2.anycommon.board.BoardDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DtoToDataModelMapper {

    public static BoardDto toDto(Board entity){
        return BoardDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdAt(toInstant(entity.getCreatedAt()))
                .build();
    }

    public static Board toEntity(BoardDto dto){
        return Board.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(toLocalDateTime(dto.getCreatedAt()))
                .build();
    }

    // LocalDateTime을 Instant로 변환
    public static Instant toInstant(LocalDateTime localDateTime) {
        // 시스템 기본 타임존으로 변환
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    // Instant를 LocalDateTime으로 변환
    public static LocalDateTime toLocalDateTime(Instant instant) {
        // 시스템 기본 타임존을 기준으로 변환
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
