package com.github.gun2.multithreadcommon.board;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BoardRepository extends JpaRepository<BoardJpaEntity, Long> {

}

