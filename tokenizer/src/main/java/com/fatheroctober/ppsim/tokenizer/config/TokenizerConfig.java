package com.fatheroctober.ppsim.tokenizer.config;

import com.fatheroctober.ppsim.common.model.CipherAlgorithm;
import com.fatheroctober.ppsim.common.util.Crypter;
import lombok.SneakyThrows;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singletonList;

@Configuration
@EnableConfigurationProperties(TokenizerProperties.class)
public class TokenizerConfig {
    private static final Logger logger = LoggerFactory.getLogger(TokenizerConfig.class);
    private static final short REPLICATION_FACTOR = 1;
    private final TokenizerProperties properties;
    private final AdminClient kafkaAdmin;

    public TokenizerConfig(TokenizerProperties properties, AdminClient kafkaAdmin) {
        this.properties = properties;
        this.kafkaAdmin = kafkaAdmin;
        createNewTopic(properties.getKafkaOutputTopic());
    }

    @Bean
    public Crypter crypter() {
        return new Crypter(CipherAlgorithm.forCode(properties.getCipherAlgorithm()));
    }

    @Bean
    @Qualifier("tokenizer-inputTopic")
    public String inputTopic() {
        return properties.getKafkaInputTopic();
    }

    @Bean
    @Qualifier("tokenizer-outputTopic")
    public String outputTopic() {
        return properties.getKafkaOutputTopic();
    }

    @SneakyThrows({InterruptedException.class, ExecutionException.class})
    private void createNewTopic(String topic) {
        logger.info("Fetching list of topic...");
        Set<String> existentTopics = kafkaAdmin.listTopics().names().get();
        logger.info("Fetched list of topic: {}", existentTopics);
        if (existentTopics.contains(topic)) {
            logger.info("Output topic {} has been already existed", topic);
            return;
        }
        logger.info("Output topic {} does not exist, trying create it...", topic);
        kafkaAdmin.createTopics(singletonList(new NewTopic(topic, properties.getPartitions(), REPLICATION_FACTOR))).all().get();
        logger.info("Output topic {} is created", topic);
    }
}
