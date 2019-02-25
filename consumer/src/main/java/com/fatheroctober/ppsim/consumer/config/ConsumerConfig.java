package com.fatheroctober.ppsim.consumer.config;

import com.fatheroctober.ppsim.common.engine.Lifecycle;
import com.fatheroctober.ppsim.common.transport.KafkaConfiguration;
import com.fatheroctober.ppsim.consumer.ConsumerLifecycle;
import com.fatheroctober.ppsim.consumer.IConsumerService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableConfigurationProperties(ConsumerProperties.class)
public class ConsumerConfig {
    private final ConsumerProperties properties;
    private final KafkaConfiguration kafkaConfiguration;
    private final IConsumerService consumerService;

    public ConsumerConfig(ConsumerProperties properties,
                          KafkaConfiguration kafkaConfiguration,
                          IConsumerService consumerService) {
        this.properties = properties;
        this.kafkaConfiguration = kafkaConfiguration;
        this.consumerService = consumerService;
    }

    @Bean
    public Path logFile() {
        if (StringUtils.isNotEmpty(properties.getDataLogFile())) {
            return Paths.get(properties.getDataLogFile());
        } else {
            Path file = Paths.get(System.getProperty("user.dir"), "consumer.log");
            return file;
        }
    }

    @Bean
    public List<Lifecycle> consumers() {
        List<Lifecycle> consumerList = Lists.newArrayList();
        for (int consumerId = 1; consumerId <= properties.getConsumerCount(); consumerId++) {
            Consumer<Long, String> kafkaConsumer = kafkaConfiguration.kafkaConsumer();
            kafkaConsumer.subscribe(Collections.singletonList(properties.getTopic()));
            consumerList.add(new ConsumerLifecycle(kafkaConsumer, consumerService, consumerId));
        }
        return consumerList;
    }
}
