package com.fatheroctober.ppsim.consumer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("consumer")
public class ConsumerProperties {

    @Getter
    @Setter
    private int consumerCount;

    @Getter
    @Setter
    private String topic;

    @Getter
    @Setter
    private String dataLogFile;


}
