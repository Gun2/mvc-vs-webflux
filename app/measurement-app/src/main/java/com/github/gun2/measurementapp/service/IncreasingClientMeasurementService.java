package com.github.gun2.measurementapp.service;

import java.util.concurrent.Future;
import java.util.function.Consumer;

public interface IncreasingClientMeasurementService {
    /**
     * 측정 전 초기화에 동작 되어야할 메서드를 정의
     */
    void ready();

    /**
     * 성능을 측정하기 위한 요청
     * @return
     * @param <T>
     */

    <T> Future<T> request(Consumer<Boolean> callback);

    void await() throws InterruptedException;
}
