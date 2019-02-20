package com.fatheroctober.ppsim.customerservice.config;

import com.fatheroctober.ppsim.customerservice.api.CustomerControllerInterceptor;
import com.fatheroctober.ppsim.customerservice.infrastructure.IPublisher;
import com.fatheroctober.ppsim.customerservice.infrastructure.KafkaPartition;
import com.fatheroctober.ppsim.customerservice.infrastructure.KafkaPublisher;
import com.fatheroctober.ppsim.customerservice.model.CustomerMessage;
import lombok.SneakyThrows;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singletonList;

@Configuration
@EnableWebMvc
@EnableConfigurationProperties(CustomerServiceProperties.class)
public class CustomerServiceConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceConfig.class);
    private static final short REPLICATION_FACTOR = 1;

    private final CustomerServiceProperties properties;
    private final AdminClient kafkaAdmin;


    public CustomerServiceConfig(CustomerServiceProperties properties, AdminClient kafkaAdmin) {
        this.properties = properties;
        this.kafkaAdmin = kafkaAdmin;
        if (properties.isCreateTopicIfNotExist()) {
            createTopicIfNotExist(properties.getKafkaTopic());
        }
    }

    @Bean
    public IPublisher<CustomerMessage> customerPublisher(Producer<Long, String> kafkaProducer) {
        return new KafkaPublisher<>(properties.getKafkaTopic(), kafkaProducer);
    }

    @Bean
    public KafkaPartition partition() {
        return new KafkaPartition(properties.getPartitions());
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomerControllerInterceptor());
    }

    @SneakyThrows({InterruptedException.class, ExecutionException.class})
    private void createTopicIfNotExist(String topic) {
        logger.info("Fetching list of topic...");
        Set<String> existentTopics = kafkaAdmin.listTopics().names().get();
        logger.info("Fetched list of topic: {}", existentTopics);
        if (existentTopics.contains(topic)) return;
        logger.info("Topic {} doesn't exist, trying create it...", topic);
        kafkaAdmin.createTopics(singletonList(new NewTopic(topic, properties.getPartitions(), REPLICATION_FACTOR))).all().get();
        logger.info("Topic {} is created", topic);
    }
}
