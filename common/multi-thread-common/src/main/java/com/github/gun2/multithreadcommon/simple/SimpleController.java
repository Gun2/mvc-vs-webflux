package com.github.gun2.multithreadcommon.simple;

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
@RequestMapping("/simples")
public class SimpleController {

    @GetMapping("/reverse/{word}")
    public String reverse(
            @PathVariable("word")Optional<String> wordOp
            ){
        return wordOp.map(string -> new StringBuffer(string).reverse().toString()).orElse("");
    }
}