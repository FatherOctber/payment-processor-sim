package com.fatheroctober.ppsim.consumer.config;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

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

    @PostConstruct
    public void validation() {
        Preconditions.checkArgument(consumerCount > 0, "consumerCount must be > 0");
        Preconditions.checkArgument(StringUtils.isNotEmpty(topic), "topic must be defined");
    }

}
