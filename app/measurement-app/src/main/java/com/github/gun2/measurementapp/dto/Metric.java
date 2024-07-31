package com.github.gun2.measurementapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@NoArgsConstructor
public class Metric {
    /**
     * 최소 수치
     */
    private Long min;
    /**
     * 최대 수치
     */
    private Long max;
    /**
     * 평균 수치
     */
    private Double avg;

    public AtomicLong recordCount = new AtomicLong();

    public Metric(Metric metric) {
        this.min = metric.getMin();
        this.max = metric.getMax();
        this.avg = metric.getAvg();
        this.recordCount = new AtomicLong(metric.getRecordCount().get());
    }

    public void record(Long result){
        if (min == null || min > result){
            this.min = result;
        }
        if (max == null || max < result) {
            this.max = result;
        }
        recordCount.incrementAndGet();
    }

    public void init(){
        this.min = null;
        this.max = null;
        this.avg = null;
        recordCount.set(0);
    }
}
