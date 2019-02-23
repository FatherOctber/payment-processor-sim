package com.fatheroctober.ppsim.common.persistence.redis;

import com.fatheroctober.ppsim.common.model.TransactionKeyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

@Configuration
public class RedisConfig {
    private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${redis.host:localhost}")
    private String redisHost;

    @Value("${redis.port:6379}")
    private int redisPort;

    @PostConstruct
    private void init() {
        logger.info("Initialized with Redis host=" + redisHost + ", port=" + redisPort);
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
        connectionFactory.setUsePool(true);
        connectionFactory.setHostName(redisHost);
        connectionFactory.setPort(redisPort);

        return connectionFactory;
    }


    @Bean
    public RedisTemplate<String, TransactionKeyRecord> redisTemplate() {
        RedisTemplate<String, TransactionKeyRecord> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setEnableTransactionSupport(true);

        RedisSerializer<TransactionKeyRecord> recordSerializer = new Jackson2JsonRedisSerializer<>(TransactionKeyRecord.class);
        template.setValueSerializer(recordSerializer);
        template.setHashValueSerializer(recordSerializer);

        return template;
    }
}
