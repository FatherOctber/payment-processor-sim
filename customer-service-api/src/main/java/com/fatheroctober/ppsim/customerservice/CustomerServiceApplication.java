package com.fatheroctober.ppsim.customerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.fatheroctober.ppsim.customerservice", "com.fatheroctober.ppsim.common"})
@SpringBootApplication
public class CustomerServiceApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(CustomerServiceApplication.class, args);
    }
}
