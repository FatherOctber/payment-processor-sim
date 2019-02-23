package com.fatheroctober.ppsim.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatheroctober.ppsim.common.model.CustomerMessage;
import com.fatheroctober.ppsim.common.model.KeyInfo;
import com.fatheroctober.ppsim.common.model.Transaction;
import com.fatheroctober.ppsim.common.persistence.operation.PullKeyFromStorage;
import com.fatheroctober.ppsim.common.util.Crypter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService implements IConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);
    private final ObjectMapper mapper;
    private final PullKeyFromStorage pullKeyOperation;

    public ConsumerService(PullKeyFromStorage pullKeyOperation) {
        this.pullKeyOperation = pullKeyOperation;
        this.mapper = new ObjectMapper();
    }

    @Override
    @SneakyThrows(JsonProcessingException.class)
    public void consume(long transactionId, String token) {
        KeyInfo keyInfo = pullKeyOperation.pull(new Transaction(transactionId));
        Crypter crypter = new Crypter(keyInfo.getKey());
        CustomerMessage customerMessage = CustomerMessage.create(crypter.decrypt(token));
        ObjectNode customerData = customerDataNode(transactionId, customerMessage);

        logger.info("Consumed data: {}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customerData));
    }

    private ObjectNode customerDataNode(long transactionId, CustomerMessage customerMessage) {
        ObjectNode customerMessageNode = mapper.createObjectNode();
        customerMessageNode.put("cardNumber", customerMessage.getPan());
        customerMessageNode.put("expirationDate", customerMessage.getExDate());
        customerMessageNode.put("cvc2", customerMessage.getCvc2());

        ObjectNode transactionNode = mapper.createObjectNode();
        transactionNode.putPOJO(String.valueOf(transactionId), customerMessageNode);
        return transactionNode;
    }
}
