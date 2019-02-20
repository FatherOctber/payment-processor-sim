package com.fatheroctober.ppsim.customerservice;

import com.fatheroctober.ppsim.customerservice.infrastructure.ILogRecord;
import com.fatheroctober.ppsim.customerservice.model.CustomerMessage;
import lombok.ToString;
import org.apache.kafka.clients.producer.ProducerRecord;

@ToString
public class CustomerLogRecord implements ILogRecord<CustomerMessage> {
    private final int partition;
    private final long transactionId;

    public CustomerLogRecord(int partition, long transactionId) {
        this.partition = partition;
        this.transactionId = transactionId;
    }

    @Override
    public ProducerRecord<Long, String> producerRecord(String topic, CustomerMessage message) {
        return new ProducerRecord<Long, String>(
                topic,
                partition,
                transactionId,
                message.serealizedContent()
        );
    }
}
