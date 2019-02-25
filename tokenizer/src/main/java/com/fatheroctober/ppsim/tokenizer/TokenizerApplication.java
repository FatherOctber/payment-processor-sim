package com.fatheroctober.ppsim.tokenizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@ComponentScan({"com.fatheroctober.ppsim.tokenizer", "com.fatheroctober.ppsim.common"})
@SpringBootApplication
public class TokenizerApplication {
    public static void main(String[] args) {
        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.setActiveProfiles("streams");

        SpringApplication sa = new SpringApplication(TokenizerApplication.class);
        sa.setEnvironment(environment);
        sa.run(args);
    }
}
