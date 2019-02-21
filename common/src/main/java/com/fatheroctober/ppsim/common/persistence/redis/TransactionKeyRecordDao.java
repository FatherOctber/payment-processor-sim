package com.fatheroctober.ppsim.common.persistence.redis;

import com.fatheroctober.ppsim.common.model.TransactionKeyRecord;
import com.fatheroctober.ppsim.common.persistence.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Repository
@Transactional
public class TransactionKeyRecordDao implements Dao<TransactionKeyRecord> {

    private static Logger logger = LoggerFactory.getLogger(TransactionKeyRecordDao.class);
    private static final String TRANS_RECORD_KEY = "TKEY:";

    @Autowired
    private RedisTemplate<String, TransactionKeyRecord> redisTemplate;
    private HashOperations<String, Long, TransactionKeyRecord> hashOps;

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    @Override
    public Optional<TransactionKeyRecord> get(Long id) {
        logger.info("Get key for id=" + id);
        Optional<TransactionKeyRecord> keyRecord = Optional.ofNullable(id).map(i -> Optional.ofNullable(hashOps.get(TRANS_RECORD_KEY, i)))
                .orElseThrow(() -> new RuntimeException("Can not perform operation for transaction with id: " + id));
        return keyRecord;
    }

    @Override
    public Long save(TransactionKeyRecord value) {
        if (value != null) {
            logger.info("Save transaction key record " + value);
            Long id = value.getTransaction().getId();
            hashOps.put(TRANS_RECORD_KEY, id, value);
            return id;
        } else {
            throw new RuntimeException("Can not save null object");
        }
    }
}
