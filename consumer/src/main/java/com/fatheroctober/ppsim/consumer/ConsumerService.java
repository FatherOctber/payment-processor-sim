package com.fatheroctober.ppsim.consumer;

import com.fatheroctober.ppsim.common.model.CustomerMessage;
import com.fatheroctober.ppsim.common.model.KeyInfo;
import com.fatheroctober.ppsim.common.model.Transaction;
import com.fatheroctober.ppsim.common.persistence.operation.PullKeyFromStorage;
import com.fatheroctober.ppsim.common.util.Crypter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService implements IConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);
    private final PullKeyFromStorage pullKeyOperation;
    private final ILogService logService;

    @Autowired
    public ConsumerService(PullKeyFromStorage pullKeyOperation,
                           ILogService logService) {
        this.pullKeyOperation = pullKeyOperation;
        this.logService = logService;
    }

    @Override
    public void consume(long transactionId, String token) {
        KeyInfo keyInfo = pullKeyOperation.pull(new Transaction(transactionId));
        if (keyInfo != null) {
            Crypter crypter = new Crypter(keyInfo.getKey());
            CustomerMessage customerMessage = CustomerMessage.create(crypter.decrypt(token));
            logService.log(transactionId, customerMessage);
        } else {
            logger.warn("Key for transaction with {} doesn`t exist", transactionId);
        }

    }
}
