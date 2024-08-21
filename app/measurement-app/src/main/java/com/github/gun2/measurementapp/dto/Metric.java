package com.github.gun2.measurementapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
    /**
     * 합계 수치
     */
    private Long sum = 0L;

    private AtomicLong recordCount = new AtomicLong();

    private long time;

    public Metric(Metric metric, long time) {
        this.min = metric.getMin();
        this.max = metric.getMax();
        this.avg = metric.getAvg();
        this.sum = metric.getSum();
        this.recordCount = new AtomicLong(metric.getRecordCount().get());
        this.time = time;
    }

    public void record(Long result){
        if (min == null || min > result){
            this.min = result;
        }
        if (max == null || max < result) {
            this.max = result;
        }
        this.sum += result;
        recordCount.incrementAndGet();
    }

    public void init(){
        this.min = null;
        this.max = null;
        this.avg = 0d;
        this.sum = 0L;
        recordCount.set(0);
    }

    /**
     * 평균값 계산
     */
    public void calcAvg(){
        this.avg = this.sum / (double)recordCount.get();
    }
}
