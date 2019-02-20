package com.fatheroctober.ppsim.customerservice;

import com.fatheroctober.ppsim.customerservice.infrastructure.ILogRecord;
import com.fatheroctober.ppsim.customerservice.infrastructure.IPublisher;
import com.fatheroctober.ppsim.customerservice.infrastructure.KafkaPartition;
import com.fatheroctober.ppsim.customerservice.model.CustomerMessage;
import com.fatheroctober.ppsim.customerservice.model.Transaction;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final IPublisher<CustomerMessage> messagePublisher;
    private final KafkaPartition partition;
    private long transactionId = 0; //todo replace it by Redis.inc


    public CustomerServiceImpl(IPublisher<CustomerMessage> messagePublisher, KafkaPartition partition) {
        this.messagePublisher = messagePublisher;
        this.partition = partition;
    }

    @Override
    public Transaction auth(String cardNumber, String expiryDate, String cvc2) {
        CustomerMessage msg = new CustomerMessage(cardNumber, expiryDate, cvc2);
        messagePublisher.publish(buildPublishingMsg(msg));
        return new Transaction(transactionId);
    }

    private Pair<ILogRecord<CustomerMessage>, CustomerMessage> buildPublishingMsg(CustomerMessage msg) {
        return Pair.of(new CustomerLogRecord(partition.getNext(), transactionId++), msg);
    }
}
