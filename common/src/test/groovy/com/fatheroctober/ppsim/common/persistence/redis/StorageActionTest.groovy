package com.fatheroctober.ppsim.common.persistence.redis

import com.fatheroctober.ppsim.common.persistence.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ValueOperations
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [RedisTestConfig.class])
class StorageActionTest extends Specification {

    @Autowired
    ValueOperations valueOperationsMock

    @Autowired
    Action storageAction

    def "increment successfully"() {
        when:
        def res = storageAction.generateUniqueId()

        then:
        1 * valueOperationsMock.increment(*_) >> { arguments ->
            assert arguments[1] == 1
            12
        }
        noExceptionThrown()

        expect:
        res == 12
    }
}
