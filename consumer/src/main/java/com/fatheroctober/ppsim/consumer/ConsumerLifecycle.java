package com.fatheroctober.ppsim.consumer;

import com.fatheroctober.ppsim.common.engine.Lifecycle;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerLifecycle implements Lifecycle, Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerLifecycle.class);
    private static final long TIRED_THRESHOLD = 10000;
    private final Consumer<Long, String> consumer;
    private final IConsumerService consumerService;
    private final long id;
    private boolean tired;
    private Thread consumerThread;

    public ConsumerLifecycle(Consumer<Long, String> consumer, IConsumerService consumerService, long consumerId) {
        this.consumer = consumer;
        this.consumerService = consumerService;
        this.id = consumerId;
        this.tired = false;
    }

    @Override
    public void run() {
        logger.info("Consumer-{} start pooling", id);
        int emptyRecordCount = 0;

        while (stillWorkable()) {
            final ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
            if (consumerRecords.count() == 0) {
                emptyRecordCount++;
                if (emptyRecordCount > TIRED_THRESHOLD) {
                    tired = true;
                }
            } else {
                consumerRecords.forEach(record -> {
                    logger.info("Consumed record from partition={}, offset={}",
                            record.partition(), record.offset());
                    consumerService.consume(record.key(), record.value());
                });
                emptyRecordCount = 0;
                consumer.commitAsync();
            }
        }
        consumer.close(); // because consumer has prototype scope
        logger.info("Stop consumer-{} polling", id);
    }

    @Override
    public void start() {
        consumerThread = new Thread(this, "Consumer-" + id);
        consumerThread.start();
    }

    @Override
    @SneakyThrows(InterruptedException.class)
    public void stop() {
        logger.info("Stop signal was taken, waiting for stopping consumer-{}...", id);
        consumerThread.interrupt();
        consumerThread.join(10000);
        logger.info("Consumer-{} was stopped", id);
    }

    private boolean stillWorkable() {
        return !tired && !Thread.currentThread().isInterrupted();
    }
}

