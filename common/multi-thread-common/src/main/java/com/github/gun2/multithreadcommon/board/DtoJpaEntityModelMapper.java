package com.github.gun2.multithreadcommon.board;

import com.github.gun2.anycommon.board.BoardDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DtoJpaEntityModelMapper {

    public static BoardDto toDto(BoardJpaEntity entity){
        return BoardDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static BoardJpaEntity toEntity(BoardDto dto){
        return BoardJpaEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
