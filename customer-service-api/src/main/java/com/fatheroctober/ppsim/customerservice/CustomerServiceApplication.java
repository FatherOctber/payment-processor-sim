package com.fatheroctober.ppsim.customerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@ComponentScan({"com.fatheroctober.ppsim.customerservice", "com.fatheroctober.ppsim.common"})
@SpringBootApplication
public class CustomerServiceApplication {
    public static void main(String[] args) {
        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.setActiveProfiles("producer", "endpoint");

        SpringApplication sa = new SpringApplication(CustomerServiceApplication.class);
        sa.setEnvironment(environment);
        sa.run(args);
    }
}
