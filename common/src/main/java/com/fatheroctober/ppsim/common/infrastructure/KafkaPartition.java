package com.fatheroctober.ppsim.common.infrastructure;

import lombok.Value;

import java.util.concurrent.atomic.AtomicInteger;

@Value
public class KafkaPartition {
    int partitionCount;

    private AtomicInteger currentPartition = new AtomicInteger(1);

    /**
     * @return next partition by round-robin principle
     */
    public int getNext() {
        if (partitionCount == 1 || currentPartition.compareAndSet(partitionCount, 1)) {
            return 1;
        } else {
            return currentPartition.incrementAndGet();
        }
    }

}
