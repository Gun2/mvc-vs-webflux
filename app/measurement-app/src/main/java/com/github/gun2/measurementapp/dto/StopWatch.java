package com.github.gun2.measurementapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StopWatch {
    private long time;

    public void start(){
        this.time = System.currentTimeMillis();
    }

    public long stop(){
        return System.currentTimeMillis() - this.time;
    }
}
