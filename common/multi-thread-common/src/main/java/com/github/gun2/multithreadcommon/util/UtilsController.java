package com.github.gun2.multithreadcommon.util;

import com.github.gun2.heavy.util.PrimeNumberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/utils")
public class UtilsController {

    @GetMapping("/reverse/{word}")
    public String reverse(
            @PathVariable("word")Optional<String> wordOp
            ){
        return wordOp.map(string -> new StringBuffer(string).reverse().toString()).orElse("");
    }

    @GetMapping("/sleep/{time}")
    public void sleep(
            @PathVariable("time")Integer time
    ){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/prime-number/{limit}")
    public Integer searchTotalPrimeNumber(
            @PathVariable("limit")Integer limit
    ){
        return PrimeNumberUtil.getTotalPrimeNumber(limit);
    }
}
