package com.fatheroctober.ppsim.customerservice.config;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

@ConfigurationProperties("customer-service")
public class CustomerServiceProperties {
    @Getter
    @Setter
    private String kafkaTopic;

    @Getter
    @Setter
    private int partitions;

    @PostConstruct
    public void validation() {
        Preconditions.checkArgument(StringUtils.isNotEmpty(kafkaTopic), "kafkaTopic must be defined");
        Preconditions.checkArgument(partitions > 0, "partitions must be > 0");
    }
}
