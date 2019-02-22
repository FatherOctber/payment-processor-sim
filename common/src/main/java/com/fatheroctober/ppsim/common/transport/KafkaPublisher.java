package com.fatheroctober.ppsim.common.transport;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaPublisher<T> implements IPublisher<T> {
    private static final Logger logger = LoggerFactory.getLogger(KafkaPublisher.class);

    private final String topic;
    private final Producer<Long, String> kafka;

    public KafkaPublisher(String topic, Producer<Long, String> producer) {
        this.topic = topic;
        this.kafka = producer;
    }

    @Override
    public void publish(Pair<ILogRecord<T>, T> data) {
        logger.trace("Sending: {}", data);
        ProducerRecord<Long, String> producerRecord = data.getKey().producerRecord(topic, data.getValue());
        kafka.send(producerRecord, (metadata, exception) -> {
            if (exception != null) {
                logger.error("Fail to send {}", data);
            } else {
                logger.info("{} was successfully send to partition {}", data, metadata.partition());
            }
        });
    }
}
