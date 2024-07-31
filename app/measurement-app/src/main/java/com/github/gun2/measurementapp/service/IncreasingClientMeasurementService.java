package com.github.gun2.measurementapp.service;

import com.github.gun2.anycommon.board.BoardDto;
import com.github.gun2.measurementapp.dto.Metric;
import com.github.gun2.measurementapp.dto.StopWatch;
import com.github.gun2.measurementapp.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 사용자를 모방한 다수의 클라이언트를 생성하고 api요청을 반복하여 성능을 측정하는 서비스
 * 클라이언트 수는 매 단계(phase)마다 증가됨
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class IncreasingClientMeasurementService {
    private final BoardApiService boardApiService;
    /**
     * 초기 요청 클라이언트 수
     */
    private final int initClient = 100;
    /**
     * 증가될 클라이언트 수
     */
    private final int increasingClient = 100;
    /**
     * 증가 단계 별 지속 시간
     */
    private final int durationMsPerPhase = 5000;
    /**
     * 총 단계
     */
    private final int phase = 10;
    private final AtomicLong currentPhase = new AtomicLong(1);
    private final Metric metric = new Metric();
    private long startTime;
    public final Map<Long, Metric> history = new HashMap<>();
    private final String outputPath = "output.json";

    public void start(){
        log.info("start init");
        init();
        log.info("completed init");

        log.info("start warmUp");
        warmUp();
        log.info("completed warmUp");

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
        increase(initClient);
    }

    public void init(){
        boardApiService.create(getSample()).block();
    }

    /**
     * 다음 단계 진행
     */
    public void nextPhase(){
        increase(this.increasingClient);
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
     * 클라이언트 개수를 증가
     */
    public void increase(int count){
        for (int i = 0; i < count; i++) {
            StopWatch stopWatch = new StopWatch();
            requestLoop();
        }
    }

    public Mono<BoardDto> request(StopWatch stopWatch){
        if(stopWatch != null){
            stopWatch.start();
        }
        return boardApiService.readById(1);
    }

    public void requestLoop(){
        StopWatch stopWatch = new StopWatch();
        this.request(stopWatch).subscribe(boardDto -> {
            long requestTime = stopWatch.stop();
            this.metric.record(requestTime);
            requestLoop();
        });
    }



    public void warmUp(){
        StopWatch stopWatch = new StopWatch();
        for (int i = 0; i < 100; i++) {
            request(stopWatch).block();
        }
    }


    public BoardDto.BoardRequest getSample() {
        return BoardDto.BoardRequest.builder()
                .title("test title")
                .content("test content")
                .build();
    }
}
