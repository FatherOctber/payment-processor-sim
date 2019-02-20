package com.fatheroctober.ppsim.customerservice.api.config

import com.fatheroctober.ppsim.customerservice.api.CustomerController
import com.fatheroctober.ppsim.customerservice.api.CustomerControllerAdvice
import com.fatheroctober.ppsim.customerservice.api.Formatter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import([
        MockedServiceConfig.class
])
class CustomerControllerTestConfig {

    @Bean
    Formatter formatter() {
        new Formatter()
    }

    @Bean
    CustomerController customerController() {
        new CustomerController()
    }

    @Bean
    CustomerControllerAdvice customerControllerAdvice() {
        new CustomerControllerAdvice()
    }
}
