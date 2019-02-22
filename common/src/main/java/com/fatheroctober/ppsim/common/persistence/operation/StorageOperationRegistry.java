package com.fatheroctober.ppsim.common.persistence.operation;

import com.fatheroctober.ppsim.common.model.TransactionKeyRecord;
import com.fatheroctober.ppsim.common.persistence.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class StorageOperationRegistry {
    private static Logger logger = LoggerFactory.getLogger(StorageOperationRegistry.class);
    private ExecutorService asyncScheduler = Executors.newCachedThreadPool();

    @Autowired
    private Dao<TransactionKeyRecord> dao;


    @Bean
    public PullKeyFromStorage pullDecryptionKey() {
        return (transaction) -> {
            logger.info("Pull for {}", transaction);
            return dao.get(transaction.getId()).map(TransactionKeyRecord::getKey).orElse(null);
        };
    }

    @Bean
    public PushKeyToStorage pushDecryptionKey() {
        return (transactionKeyRecord) -> {
            logger.info("Push {}", transactionKeyRecord);
            asyncScheduler.execute(() -> dao.save(transactionKeyRecord));
        };
    }
}
