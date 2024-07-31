package com.github.gun2.measurementapp.service;

import com.github.gun2.measurementapp.dto.Metric;
import com.github.gun2.measurementapp.dto.StopWatch;
import com.github.gun2.measurementapp.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 사용자를 모방한 다수의 클라이언트를 생성하고 api요청을 반복하여 성능을 측정하는 추상 서비스
 * 클라이언트 수는 매 단계(phase)마다 증가되며 단계별로 수행된 요청 정보를 기록
 */
@Slf4j
public abstract class TemplateIncreasingClientMeasurementService implements IncreasingClientMeasurementService{
    /**
     * 초기 요청 클라이언트 수
     */
    private final int initClient;
    /**
     * 증가될 클라이언트 수
     */
    private final int increasingClient;
    /**
     * 증가 단계 별 지속 시간
     */
    private final int durationMsPerPhase;
    /**
     * 총 단계
     */
    private final int phase;
    private final AtomicLong currentPhase = new AtomicLong(1);
    private final Metric metric = new Metric();
    private long startTime;
    public final Map<Long, Metric> history = new HashMap<>();
    private final String outputPath;

    public TemplateIncreasingClientMeasurementService(TemplateIncreasingClientMeasurementConfig config) {
        this.initClient = config.getInitClient();
        this.increasingClient = config.getIncreasingClient();
        this.durationMsPerPhase = config.getDurationMsPerPhase();
        this.phase = config.getPhase();
        this.outputPath = config.getOutputPath();
    }

    public void start(){
        log.info("start init");
        ready();
        log.info("completed init");

        log.info("start fire");
        fire();
        log.info("completed fire");

    }

    /**
     * 측정 시작
     */
    private void fire() {
        ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
        schedule.scheduleAtFixedRate(() -> {
            recordMetric();
            if (phase > currentPhase.get()){
                nextPhase();
            }else {
                try {
                    FileUtil.saveObjectToFile(outputPath, this.history);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                schedule.shutdown();
            }
        }, durationMsPerPhase, durationMsPerPhase, TimeUnit.MILLISECONDS);
        startTime = System.currentTimeMillis();
        log.info("start request");
        increaseRequest(initClient);
    }

    /**
     * 다음 단계 진행
     */
    public void nextPhase(){
        increaseRequest(this.increasingClient);
        long next = currentPhase.incrementAndGet();
        log.info("go to {} phase", next);
    }

    /**
     * 측정 정보 기록
     */
    public void recordMetric(){
        this.history.put(System.currentTimeMillis() - startTime, new Metric(this.metric));
        this.metric.init();
        log.info("record metric");
    }

    /**
     * 동시 요청 개수를 증가
     * @param count 증가시킬 동시 요청 개수
     */
    public void increaseRequest(int count){
        for (int i = 0; i < count; i++) {
            StopWatch stopWatch = new StopWatch();
            startRequestAndRecordLoop();
        }
    }

    /**
     * 반복적으로 요청을 수행하고 기록하는 루프 동작
     */
    public void startRequestAndRecordLoop(){
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        this.request().subscribe(boardDto -> {
            long requestTime = stopWatch.stop();
            this.metric.record(requestTime);
            startRequestAndRecordLoop();
        });
    }
}
