package com.fatheroctober.ppsim.consumer;

public interface IConsumerService {
    void consume(long transactionId, String token);
}
