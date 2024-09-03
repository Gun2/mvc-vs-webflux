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

    private AtomicLong successCount = new AtomicLong();
    private AtomicLong failureCount = new AtomicLong();

    private long time;

    public Metric(Metric metric, long time) {
        this.min = metric.getMin();
        this.max = metric.getMax();
        this.avg = metric.getAvg();
        this.sum = metric.getSum();
        this.successCount = new AtomicLong(metric.getSuccessCount().get());
        this.failureCount = new AtomicLong(metric.getFailureCount().get());
        this.time = time;
    }

    public void record(Long result, boolean success){
        if (min == null || min > result){
            this.min = result;
        }
        if (max == null || max < result) {
            this.max = result;
        }
        this.sum += result;
        if (success){
            this.successCount.incrementAndGet();
        }else {
            this.failureCount.incrementAndGet();
        }
    }

    public void init(){
        this.min = null;
        this.max = null;
        this.avg = 0d;
        this.sum = 0L;
        this.successCount.set(0);
        this.failureCount.set(0);
    }

    /**
     * 평균값 계산
     */
    public void calcAvg(){
        this.avg = this.sum / (double)(successCount.get() + failureCount.get());
    }
}
