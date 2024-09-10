package com.github.gun2.measurementapp.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.core5.util.TimeValue;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeasurementHttpClientFactory {
    public static CloseableHttpAsyncClient create(){

        PoolingAsyncClientConnectionManager poolingAsyncClientConnectionManager = new PoolingAsyncClientConnectionManager();
        poolingAsyncClientConnectionManager.setDefaultMaxPerRoute(Integer.MAX_VALUE);
        poolingAsyncClientConnectionManager.setMaxTotal(Integer.MAX_VALUE);
        return HttpAsyncClients.custom()
                .setRetryStrategy(new DefaultHttpRequestRetryStrategy(0, TimeValue.ZERO_MILLISECONDS))
                .setConnectionManager(poolingAsyncClientConnectionManager)
                .build();
    }
}
