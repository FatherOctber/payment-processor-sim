package com.fatheroctober.ppsim.common.persistence.operation;

import com.fatheroctober.ppsim.common.model.TransactionKeyRecord;
import com.fatheroctober.ppsim.common.persistence.Action;
import com.fatheroctober.ppsim.common.persistence.Dao;
import com.google.common.base.Throwables;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class StorageOperationRegistry {
    private static Logger logger = LoggerFactory.getLogger(StorageOperationRegistry.class);
    private ExecutorService asyncScheduler = Executors.newCachedThreadPool();

    @Autowired
    private Dao<TransactionKeyRecord> dao;

    @Autowired
    private Action action;


    @Bean
    public PullKeyFromStorage pullDecryptionKey() {
        return (transaction) -> {
            logger.info("Pull key for {}", transaction);
            try {
                return asyncScheduler.submit(() -> dao.get(transaction.getId()))
                        .get()
                        .map(TransactionKeyRecord::getKey)
                        .orElse(null);
            } catch (Exception e) {
                throw Throwables.propagate(e);
            }
        };
    }

    @Bean
    public PushKeyToStorage pushDecryptionKey() {
        return (transactionKeyRecord) -> {
            logger.info("Push key {}", transactionKeyRecord);
            asyncScheduler.submit(() -> dao.save(transactionKeyRecord));
        };
    }

    @Bean
    public GetUniqueId getUniqueId() {
        return () -> {
            logger.info("Get unique id");
            try {
                return asyncScheduler.submit(() -> action.generateUniqueId()).get();
            } catch (Exception e) {
                throw Throwables.propagate(e);
            }
        };
    }
}
