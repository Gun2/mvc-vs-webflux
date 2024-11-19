package com.github.gun2.reactorcommon.util;

import com.github.gun2.heavy.util.PrimeNumberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/utils")
public class UtilsController {
    @GetMapping("/reverse/{word}")
    public Mono<String> reverse(
            @PathVariable("word") Optional<String> wordOp
    ) {
        return Mono.just(
                wordOp.map(string -> new StringBuffer(string).reverse().toString()).orElse("")
        );
    }

    @GetMapping("/sleep/{time}")
    public Mono<Void> sleep(
            @PathVariable("time") Integer time
    ) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Mono.empty();
    }

    @GetMapping("/prime-number/{limit}")
    public Mono<Integer> searchTotalPrimeNumber(
            @PathVariable("limit") Integer cycle
    ) {
        return Mono.just(PrimeNumberUtil.getTotalPrimeNumber(cycle));
    }

    @GetMapping("/prime-number/{limit}/with-context-switch")
    public Mono<Integer> searchTotalPrimeNumberWithContextSwitch(
            @PathVariable("limit") Integer cycle
    ) {
        return Mono.just(PrimeNumberUtil.getTotalPrimeNumberWithContextSwitch(cycle));
    }
}
