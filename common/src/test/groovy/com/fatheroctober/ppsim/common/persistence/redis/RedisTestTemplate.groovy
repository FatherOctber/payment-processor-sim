package com.fatheroctober.ppsim.common.persistence.redis

import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations

class RedisTestTemplate<K, V> extends RedisTemplate<K, V> {
    private HashOperations hashMock
    private ValueOperations valueMock

    RedisTestTemplate(HashOperations hashMock, ValueOperations valueMock) {
        this.hashMock = hashMock
        this.valueMock = valueMock
    }

    @Override
    void afterPropertiesSet() {
        //check nothing
    }

    @Override
    <HK, HV> HashOperations<K, HK, HV> opsForHash() {
        return hashMock
    }

    @Override
    ValueOperations<K, V> opsForValue() {
        return valueMock
    }
}
