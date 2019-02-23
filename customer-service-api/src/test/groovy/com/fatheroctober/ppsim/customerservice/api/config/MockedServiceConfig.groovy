package com.fatheroctober.ppsim.customerservice.api.config

import com.fatheroctober.ppsim.customerservice.ICustomerService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import spock.mock.DetachedMockFactory

@Configuration
class MockedServiceConfig {
    def mockFactory = new DetachedMockFactory()

    @Bean
    ICustomerService customerService() {
        mockFactory.Mock(ICustomerService)
    }

}
