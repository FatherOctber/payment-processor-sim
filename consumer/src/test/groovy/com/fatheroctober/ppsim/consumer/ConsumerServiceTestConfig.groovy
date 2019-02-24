package com.fatheroctober.ppsim.consumer

import com.fatheroctober.ppsim.common.persistence.operation.PullKeyFromStorage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import spock.mock.DetachedMockFactory

@Configuration
class ConsumerServiceTestConfig {
    def mockFactory = new DetachedMockFactory()

    @Bean
    ILogService logService() {
        mockFactory.Mock(ILogService)
    }

    @Bean
    PullKeyFromStorage pullKeyFromStorage() {
        mockFactory.Mock(PullKeyFromStorage)
    }

    @Bean
    IConsumerService consumerService() {
        new ConsumerService(pullKeyFromStorage(), logService())
    }
}
