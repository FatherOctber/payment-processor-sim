package com.fatheroctober.ppsim.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.fatheroctober.ppsim.consumer", "com.fatheroctober.ppsim.common"})
@SpringBootApplication
public class ConsumerApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ConsumerApplication.class, args);
    }
}
