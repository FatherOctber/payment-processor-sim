package com.fatheroctober.ppsim.customerservice

import com.fatheroctober.ppsim.common.infrastructure.IPublisher
import com.fatheroctober.ppsim.common.infrastructure.KafkaPartition
import com.fatheroctober.ppsim.common.model.CustomerMessage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import spock.mock.DetachedMockFactory

@Configuration
class CustomerServiceTestConfig {
    def mockFactory = new DetachedMockFactory()

    @Bean
    IPublisher<CustomerMessage> messagePublisher() {
        mockFactory.Mock(IPublisher)
    }

    @Bean
    KafkaPartition kafkaPartition() {
        new KafkaPartition(3)
    }

    @Bean
    ICustomerService customerService() {
        new CustomerServiceImpl(messagePublisher(), kafkaPartition())
    }
}
