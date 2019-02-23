package com.fatheroctober.ppsim.customerservice

import com.fatheroctober.ppsim.common.transport.IPublisher
import com.fatheroctober.ppsim.common.transport.KafkaPartition
import com.fatheroctober.ppsim.common.model.CustomerMessage
import com.fatheroctober.ppsim.common.persistence.operation.GetUniqueId
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
    GetUniqueId getUniqueId() {
        mockFactory.Mock(GetUniqueId)
    }

    @Bean
    KafkaPartition kafkaPartition() {
        new KafkaPartition(3)
    }

    @Bean
    ICustomerService customerService() {
        new CustomerService(messagePublisher(), kafkaPartition(), getUniqueId())
    }
}
