package com.github.gun2.measurementapp.properties;

import lombok.Getter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppInfo {
    //읽기 성능 측정 설정값
    private final MeasurementConfig readMeasurementConfig;
    //쓰기 성능 측정 설정값
    private final MeasurementConfig createMeasurementConfig;

    public AppInfo(Environment environment) {
        this.readMeasurementConfig = MeasurementConfig.ofReadReadProps(environment);
        this.createMeasurementConfig = MeasurementConfig.ofCreateReadProps(environment);

    }

    @Getter
    public static class MeasurementConfig {
        private final int initClient;
        private final int increasingClient;
        private final int durationMsPerPhase;
        private final int phase;
        private final String outputPath;

        public MeasurementConfig(int initClient, int increasingClient, int durationMsPerPhase, int phase, String outputPath) {
            this.initClient = initClient;
            this.increasingClient = increasingClient;
            this.durationMsPerPhase = durationMsPerPhase;
            this.phase = phase;
            this.outputPath = outputPath;
        }

        public static MeasurementConfig ofReadReadProps(Environment environment){
            return new MeasurementConfig(
                    environment.getProperty("app.read.measurement.config.init-client", Integer.class),
                    environment.getProperty("app.read.measurement.config.increasing-client", Integer.class),
                    environment.getProperty("app.read.measurement.config.duration-ms-per-phase", Integer.class),
                    environment.getProperty("app.read.measurement.config.phase", Integer.class),
                    environment.getProperty("app.read.measurement.config.output-path", String.class)
            );
        }

        public static MeasurementConfig ofCreateReadProps(Environment environment){
            return new MeasurementConfig(
                    environment.getProperty("app.create.measurement.config.init-client", Integer.class),
                    environment.getProperty("app.create.measurement.config.increasing-client", Integer.class),
                    environment.getProperty("app.create.measurement.config.duration-ms-per-phase", Integer.class),
                    environment.getProperty("app.create.measurement.config.phase", Integer.class),
                    environment.getProperty("app.create.measurement.config.output-path", String.class)
            );
        }
    }
}
