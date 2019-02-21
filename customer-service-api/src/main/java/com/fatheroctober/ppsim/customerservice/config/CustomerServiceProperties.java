package com.fatheroctober.ppsim.customerservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("customer-service")
public class CustomerServiceProperties {

    @Getter
    @Setter
    private boolean createTopicIfNotExist;

    @Getter
    @Setter
    private String kafkaTopic;

    @Getter
    @Setter
    private int partitions;
}
