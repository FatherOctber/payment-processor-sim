package com.fatheroctober.ppsim.customerservice;

import com.fatheroctober.ppsim.common.transport.ILogRecord;
import com.fatheroctober.ppsim.common.transport.IPublisher;
import com.fatheroctober.ppsim.common.transport.KafkaPartition;
import com.fatheroctober.ppsim.common.model.CustomerMessage;
import com.fatheroctober.ppsim.common.model.Transaction;
import com.fatheroctober.ppsim.common.persistence.operation.GetUniqueId;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {

    private final IPublisher<CustomerMessage> messagePublisher;
    private final KafkaPartition partition;
    private final GetUniqueId uniqueId;

    public CustomerService(IPublisher<CustomerMessage> messagePublisher,
                           KafkaPartition partition,
                           GetUniqueId uniqueId) {
        this.messagePublisher = messagePublisher;
        this.partition = partition;
        this.uniqueId = uniqueId;
    }

    @Override
    public Transaction auth(String cardNumber, String expiryDate, String cvc2) {
        CustomerMessage msg = new CustomerMessage(cardNumber, expiryDate, cvc2);
        Long transactionId = uniqueId.get();
        messagePublisher.publish(buildPublishingMsg(msg, transactionId));
        return new Transaction(transactionId);
    }

    private Pair<ILogRecord<CustomerMessage>, CustomerMessage> buildPublishingMsg(CustomerMessage msg, Long tansId) {
        return Pair.of(new CustomerLogRecord(partition.getNext(), tansId), msg);
    }
}
