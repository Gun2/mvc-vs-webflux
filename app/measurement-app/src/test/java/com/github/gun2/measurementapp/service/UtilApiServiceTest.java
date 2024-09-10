package com.github.gun2.measurementapp.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
@Slf4j
class UtilApiServiceTest {

    UtilApiService utilApiService = new UtilApiService() ;

    @Test
    @DisplayName("응답시간이 3초인 API를 동시에 100개를 호출할 때 5초 안에 모든 API가 응답 하는지 테스트")
    void nonBlockingExecuteTest() throws InterruptedException {
        /** given */
        int limit = 100;
        CountDownLatch countDownLatch = new CountDownLatch(limit);

        /** when */
        Stream.generate(() -> new Thread(() -> {
            utilApiService.complex("http://localhost:8081", 3000, new FutureCallback<SimpleHttpResponse>() {
                @Override
                public void completed(SimpleHttpResponse simpleHttpResponse) {
                    log.info("completed");
                    countDownLatch.countDown();
                }

                @Override
                public void failed(Exception e) {
                    log.error("{0}", e);
                }

                @Override
                public void cancelled() {

                }
            });
        })).limit(limit).forEach(Thread::start);

        /** then */
        countDownLatch.await(5, TimeUnit.SECONDS);
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }
}