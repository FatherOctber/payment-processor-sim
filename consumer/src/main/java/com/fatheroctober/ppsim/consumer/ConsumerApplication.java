package com.fatheroctober.ppsim.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@ComponentScan({"com.fatheroctober.ppsim.consumer", "com.fatheroctober.ppsim.common"})
@SpringBootApplication
public class ConsumerApplication {
    public static void main(String[] args) {
        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.setActiveProfiles("consumer");

        SpringApplication sa = new SpringApplication(ConsumerApplication.class);
        sa.setEnvironment(environment);
        sa.run(args);
    }
}
