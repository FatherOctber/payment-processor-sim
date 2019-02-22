package com.fatheroctober.ppsim.common.model

import spock.lang.Specification

class CustomerMessageTest extends Specification {

    def "serialize and deserialize successfully"() {
        given:
        def msg = new CustomerMessage('123412341234', '12/12', '007')

        when:
        def serealized = msg.serializedContent()
        def res = CustomerMessage.create(serealized)

        then:
        noExceptionThrown()

        expect:
        res == msg
    }
}
