package com.github.gun2.measurementapp.service;

import com.github.gun2.measurementapp.dto.Metric;
import com.github.gun2.measurementapp.dto.StopWatch;
import com.github.gun2.measurementapp.util.FileUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 사용자를 모방한 다수의 클라이언트를 생성하고 api요청을 반복하여 성능을 측정하는 추상 서비스
 * 클라이언트 수는 매 단계(phase)마다 증가되며 단계별로 수행된 요청 정보를 기록
 */
@Slf4j
public abstract class TemplateIncreasingClientMeasurementService implements IncreasingClientMeasurementService {
    /**
     * 대상 url
     */
    protected final String targetUrl;
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
    @Getter
    public final List<Metric> history = new ArrayList<>();
    private final String outputPath;
    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private boolean start;

    public TemplateIncreasingClientMeasurementService(TemplateIncreasingClientMeasurementConfig config) {
        this.targetUrl = config.getTargetUrl();
        this.initClient = config.getInitClient();
        this.increasingClient = config.getIncreasingClient();
        this.durationMsPerPhase = config.getDurationMsPerPhase();
        this.phase = config.getPhase();
        this.outputPath = config.getOutputPath();
    }

    public void start(){
        if (this.start){
            throw new IllegalStateException("Already started");
        }
        this.start = true;
        log.info("start initialization");
        ready();
        log.info("init is completed");

        log.info("start firing");
        fire();
    }

    /**
     * 측정 시작
     */
    private void fire() {
        ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
        schedule.scheduleAtFixedRate(() -> {
            recordMetric();
            if (phase > currentPhase.get()) {
                nextPhase();
            } else {
                try {
                    FileUtil.saveObjectToFile(outputPath, this.history);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                schedule.shutdown();
                countDownLatch.countDown();
                log.info("firing is complete");
                this.start = false;
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
        log.info("phase({}/{})", next, this.phase);
    }

    /**
     * 측정 정보 기록
     */
    public void recordMetric(){
        this.metric.calcAvg();
        Metric historyMetric = new Metric(this.metric, System.currentTimeMillis() - startTime);
        this.history.add(historyMetric);
        log.info("record metric : {}", historyMetric);
        this.metric.init();
    }

    /**
     * 동시 요청 개수를 증가
     * @param count 증가시킬 동시 요청 개수
     */
    public void increaseRequest(int count){
        for (int i = 0; i < count; i++) {
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
            if (this.start){
                startRequestAndRecordLoop();
            }
        });
    }

    /**
     * 작업이 수행될 때 까지 대기
     * @throws InterruptedException
     */
    @Override
    public void await() throws InterruptedException {
        if (this.start){
            this.countDownLatch.await();
        }
    }
}
