package com.fatheroctober.ppsim.common.persistence.redis;

import com.fatheroctober.ppsim.common.persistence.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Repository
@Transactional
public class StorageAction implements Action {
    private static Logger logger = LoggerFactory.getLogger(StorageAction.class);
    private static final String ID_KEY = "ID:";

    @Autowired
    private RedisTemplate redisTemplate;
    private ValueOperations incrOps;

    @PostConstruct
    private void init() {
        incrOps = redisTemplate.opsForValue();
    }

    @Override
    public Long generateUniqueId() {
        logger.info("Generate unique id");
        return incrOps.increment(ID_KEY, 1L);

    }
}
