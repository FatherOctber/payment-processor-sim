package com.fatheroctober.ppsim.tokenizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.fatheroctober.ppsim.tokenizer", "com.fatheroctober.ppsim.common"})
@SpringBootApplication
public class TokenizerApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(TokenizerApplication.class, args);
    }
}
