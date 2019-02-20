package com.fatheroctober.ppsim.customerservice

import com.fatheroctober.ppsim.infrastructure.ILogRecord
import com.fatheroctober.ppsim.infrastructure.IPublisher
import com.fatheroctober.ppsim.model.CustomerMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [CustomerServiceTestConfig.class])
class CustomerServiceTest extends Specification {

    @Autowired
    ICustomerService customerService

    @Autowired
    IPublisher<CustomerMessage> messagePublisher

    def "service send customer info successfully"() {
        when:
        def transaction1 = customerService.auth("123412341234", "12/12", "777")
        def transaction2 = customerService.auth("123412341234", "12/12", "777")
        def transaction3 = customerService.auth("123412341234", "12/12", "777")

        then:
        3 * messagePublisher.publish(*_) >> { arguments ->
            assert arguments[0].getLeft() instanceof ILogRecord<CustomerMessage>
            assert arguments[0].getRight() instanceof CustomerMessage
        }

        expect:
        transaction1.id == 1
        transaction2.id == 2
        transaction3.id == 3

    }

}
