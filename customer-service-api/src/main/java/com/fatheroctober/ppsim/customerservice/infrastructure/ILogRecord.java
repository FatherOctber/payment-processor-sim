package com.fatheroctober.ppsim.customerservice.infrastructure;

import org.apache.kafka.clients.producer.ProducerRecord;

public interface ILogRecord<T> {
    ProducerRecord<Long, String> producerRecord(String topic, T message);
}
