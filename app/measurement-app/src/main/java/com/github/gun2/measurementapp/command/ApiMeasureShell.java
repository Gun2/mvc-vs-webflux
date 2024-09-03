package com.github.gun2.measurementapp.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gun2.measurementapp.component.BoardApiIncreasingClientMeasurementServiceFactory;
import com.github.gun2.measurementapp.component.OtherApiIncreasingClientMeasurementServiceFactory;
import com.github.gun2.measurementapp.service.BoardCreateIncreasingClientMeasurementService;
import com.github.gun2.measurementapp.service.BoardReadIncreasingClientMeasurementService;
import com.github.gun2.measurementapp.service.SimpleIncreasingClientMeasurementService;
import com.github.gun2.measurementapp.service.TemplateIncreasingClientMeasurementConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class ApiMeasureShell {
    private final BoardApiIncreasingClientMeasurementServiceFactory boardApiIncreasingClientMeasurementServiceFactory;
    private final OtherApiIncreasingClientMeasurementServiceFactory otherApiIncreasingClientMeasurementServiceFactory;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @ShellMethod(key = "read", prefix = "-", value = "조회 API 성능 측정")
    public String readApiMeasure(
            @ShellOption(
                    value = {"u", "targetUrl"},
                    help = "대상 서버 url"
            ) String targetUrl,
            @ShellOption(
                    value = {"t", "initClient"},
                    help = "초기 요청 클라이언트 수.",
                    defaultValue = "1"
            ) Integer initClient,
            @ShellOption(
                    value = {"i", "increasingClient"},
                    help = "매 단계마다 증가될 클라이언트 수",
                    defaultValue = "1"
            ) Integer increasingClient,
            @ShellOption(
                    value = {"d", "durationMsPerPhase"},
                    help = "단계의 지속 시간",
                    defaultValue = "5000"
            ) Integer durationMsPerPhase,
            @ShellOption(
                    value = {"p", "phase"},
                    help = "총 단계",
                    defaultValue = "10"
            ) Integer phase,
            @ShellOption(
                    value = {"o", "outputPath"},
                    help = "측정 결과 저장 경로",
                    defaultValue = "read_api_measure_output.json"
            ) String outputPath
            ) throws InterruptedException, JsonProcessingException {
        BoardReadIncreasingClientMeasurementService readApiMeasurementService = boardApiIncreasingClientMeasurementServiceFactory.createReadApiMeasurementService(TemplateIncreasingClientMeasurementConfig.builder()
                .targetUrl(targetUrl)
                .initClient(initClient)
                .increasingClient(increasingClient)
                .durationMsPerPhase(durationMsPerPhase)
                .phase(phase)
                .outputPath(outputPath)
                .build());
        readApiMeasurementService.start();
        readApiMeasurementService.await();
        return objectMapper.writeValueAsString(readApiMeasurementService.getHistory());
    }


    @ShellMethod(key = "create", prefix = "-", value = "생성 API 성능 측정")
    public String createApiMeasure(
            @ShellOption(
                    value = {"u", "targetUrl"},
                    help = "대상 서버 url"
            ) String targetUrl,
            @ShellOption(
                    value = {"t", "initClient"},
                    help = "초기 요청 클라이언트 수.",
                    defaultValue = "1"
            ) Integer initClient,
            @ShellOption(
                    value = {"i", "increasingClient"},
                    help = "매 단계마다 증가될 클라이언트 수",
                    defaultValue = "1"
            ) Integer increasingClient,
            @ShellOption(
                    value = {"d", "durationMsPerPhase"},
                    help = "단계의 지속 시간",
                    defaultValue = "5000"
            ) Integer durationMsPerPhase,
            @ShellOption(
                    value = {"p", "phase"},
                    help = "총 단계",
                    defaultValue = "10"
            ) Integer phase,
            @ShellOption(
                    value = {"o", "outputPath"},
                    help = "측정 결과 저장 경로",
                    defaultValue = "create_api_measure_output.json"
            ) String outputPath
    ) throws InterruptedException, JsonProcessingException {
        BoardCreateIncreasingClientMeasurementService createApiMeasurementService = boardApiIncreasingClientMeasurementServiceFactory.createCreateApiMeasurementService(TemplateIncreasingClientMeasurementConfig.builder()
                .targetUrl(targetUrl)
                .initClient(initClient)
                .increasingClient(increasingClient)
                .durationMsPerPhase(durationMsPerPhase)
                .phase(phase)
                .outputPath(outputPath)
                .build());
        createApiMeasurementService.start();
        createApiMeasurementService.await();
        return objectMapper.writeValueAsString(createApiMeasurementService.getHistory());
    }

    @ShellMethod(key = "simple", prefix = "-", value = "가벼운 기능의 API 성능 측정")
    public String simpleApiMeasure(
            @ShellOption(
                    value = {"u", "targetUrl"},
                    help = "대상 서버 url"
            ) String targetUrl,
            @ShellOption(
                    value = {"t", "initClient"},
                    help = "초기 요청 클라이언트 수.",
                    defaultValue = "1"
            ) Integer initClient,
            @ShellOption(
                    value = {"i", "increasingClient"},
                    help = "매 단계마다 증가될 클라이언트 수",
                    defaultValue = "1"
            ) Integer increasingClient,
            @ShellOption(
                    value = {"d", "durationMsPerPhase"},
                    help = "단계의 지속 시간",
                    defaultValue = "5000"
            ) Integer durationMsPerPhase,
            @ShellOption(
                    value = {"p", "phase"},
                    help = "총 단계",
                    defaultValue = "10"
            ) Integer phase,
            @ShellOption(
                    value = {"o", "outputPath"},
                    help = "측정 결과 저장 경로",
                    defaultValue = "simple_api_measure_output.json"
            ) String outputPath
    ) throws InterruptedException, JsonProcessingException {
        SimpleIncreasingClientMeasurementService simpleApiMeasurementService = otherApiIncreasingClientMeasurementServiceFactory.createSimpleApiMeasurementService(TemplateIncreasingClientMeasurementConfig.builder()
                .targetUrl(targetUrl)
                .initClient(initClient)
                .increasingClient(increasingClient)
                .durationMsPerPhase(durationMsPerPhase)
                .phase(phase)
                .outputPath(outputPath)
                .build());
        simpleApiMeasurementService.start();
        simpleApiMeasurementService.await();
        return objectMapper.writeValueAsString(simpleApiMeasurementService.getHistory());
    }
}
