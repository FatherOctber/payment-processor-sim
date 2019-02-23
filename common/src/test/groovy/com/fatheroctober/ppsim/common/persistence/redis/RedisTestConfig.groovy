package com.fatheroctober.ppsim.common.persistence.redis

import com.fatheroctober.ppsim.common.model.TransactionKeyRecord
import com.fatheroctober.ppsim.common.persistence.Action
import com.fatheroctober.ppsim.common.persistence.Dao
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import spock.mock.DetachedMockFactory

@Configuration
class RedisTestConfig {

    def mockFactory = new DetachedMockFactory()

    @Bean
    HashOperations<String, Long, TransactionKeyRecord> hashOperations() {
        mockFactory.Mock(HashOperations)
    }

    @Bean
    ValueOperations<String, TransactionKeyRecord> valueOperations() {
        mockFactory.Mock(ValueOperations)
    }

    @Bean
    RedisTemplate<String, TransactionKeyRecord> redisTemplate() {
        new RedisTestTemplate<String, TransactionKeyRecord>(hashOperations(), valueOperations())
    }

    @Bean
    Dao<TransactionKeyRecord> dao() {
        new TransactionKeyRecordDao()
    }

    @Bean
    Action action() {
        new StorageAction()
    }
}
