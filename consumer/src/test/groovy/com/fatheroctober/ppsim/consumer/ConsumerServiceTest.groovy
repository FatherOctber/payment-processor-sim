package com.fatheroctober.ppsim.consumer

import com.fatheroctober.ppsim.common.model.CipherAlgorithm
import com.fatheroctober.ppsim.common.model.CustomerMessage
import com.fatheroctober.ppsim.common.model.Transaction
import com.fatheroctober.ppsim.common.persistence.operation.PullKeyFromStorage
import com.fatheroctober.ppsim.common.util.Crypter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [ConsumerServiceTestConfig.class])
class ConsumerServiceTest extends Specification {
    @Autowired
    IConsumerService consumerService

    @Autowired
    PullKeyFromStorage pullKeyFromStorage

    @Autowired
    ILogService logService

    def "consume successfully"() {
        given:
        def customerData = new CustomerMessage('123412341234', '12/12', '007')
        def crypter = new Crypter(CipherAlgorithm.AES)
        def token = crypter.encrypt(customerData.serializedContent())

        when:
        consumerService.consume(11, token)

        then:
        1 * pullKeyFromStorage.pull(*_) >> { arguments ->
            assert arguments[0] == new Transaction(11)
            crypter.keyInfo()
        }
        1 * logService.log(*_) >> { arguments ->
            assert arguments[0] == 11
            assert arguments[1] == customerData
        }
        noExceptionThrown()
    }
}
