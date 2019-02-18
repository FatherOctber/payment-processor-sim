package com.fatheroctober.ppsim.customerservice.infrastructure;

import com.google.common.collect.ImmutableMap;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class KafkaConfiguration {

    @Value("${kafka.bootstrap.servers}")
    private String kafkaBrokers;

    @Bean(destroyMethod = "close")
    public Producer<Long, String> kafkaProducer() {
        Map<String, Object> config = ImmutableMap.<String, Object>builder().
                put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class).
                put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class).
                put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokers).
                put(ProducerConfig.ACKS_CONFIG, "all").
                put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE).
                put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1).
                put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, (int) TimeUnit.HOURS.toMillis(1)).
                put(ProducerConfig.BUFFER_MEMORY_CONFIG, 1 * 1024 * 1024).
                put(ProducerConfig.MAX_BLOCK_MS_CONFIG, TimeUnit.HOURS.toMillis(1)).
                build();
        return new KafkaProducer<>(config);
    }

    @Bean(destroyMethod = "close")
    public Consumer<Long, String> kafkaConsumer() {
        Map<String, Object> config = ImmutableMap.of(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokers
        );
        return new KafkaConsumer<>(config);
    }

    @Bean(destroyMethod = "close")
    public AdminClient kafkaAdminClient() {
        return KafkaAdminClient.create(ImmutableMap.<String, Object>builder().
                put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokers).
                put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, (int) TimeUnit.MINUTES.toMillis(1)).
                put(AdminClientConfig.RETRIES_CONFIG, 1).
                build()
        );
    }

}
