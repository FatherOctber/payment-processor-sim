package com.fatheroctober.ppsim.common.persistence.redis

import com.fatheroctober.ppsim.common.model.CipherAlgorithm
import com.fatheroctober.ppsim.common.model.Transaction
import com.fatheroctober.ppsim.common.model.TransactionKeyRecord
import com.fatheroctober.ppsim.common.persistence.Dao
import com.fatheroctober.ppsim.common.util.Crypter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.HashOperations
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [RedisTestConfig.class])
class TransactionKeyRecordDaoTest extends Specification {

    @Autowired
    HashOperations<String, Long, TransactionKeyRecord> hashOperationsMock

    @Autowired
    Dao<TransactionKeyRecord> urlDao


    def "save operation finished successfully"() {
        given:
        def record = createTransactionRecord()

        when:
        def res = urlDao.save(record)

        then:
        1 * hashOperationsMock.put(*_) >> { arguments ->
            assert arguments[1] == 12
            assert arguments[2] == record
        }
        noExceptionThrown()

        expect:
        res == record.transaction.id
    }

    def "get operation finished successfully"() {
        given:
        def record = createTransactionRecord()

        when:
        def res = urlDao.get(12)

        then:
        1 * hashOperationsMock.get(*_) >> { arguments ->
            assert arguments[1] == 12
            record
        }
        noExceptionThrown()

        expect:
        res == Optional.of(record)
    }


    def "null object saving throws exception"() {
        when:
        urlDao.save(null)

        then:
        thrown(RuntimeException)
    }

    def createTransactionRecord() {
        new TransactionKeyRecord(new Crypter(CipherAlgorithm.AES).keyInfo(), new Transaction(12))
    }
}