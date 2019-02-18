package com.fatheroctober.ppsim.customerservice.api

import com.fatheroctober.ppsim.customerservice.api.config.CustomerServiceTestConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [CustomerServiceTestConfig.class])
class FormatterTest extends Specification {
    @Autowired
    Formatter formatter

    def "formatter validate successfully"() {
        given:
        CardInfo cardInfo = new CardInfo().cardNumber("123412341234").expirationDate("12/12").cvc2("123")

        when:
        formatter.validateCardInfo(cardInfo)

        then:
        noExceptionThrown()
    }


    def "wrong pan"() {
        given:
        CardInfo cardInfo = new CardInfo().cardNumber("123fefefw")

        when:
        formatter.validateCardInfo(cardInfo)

        then:
        def throwable = thrown(IllegalArgumentException)

        expect:
        throwable.message == "Bad pan format"
    }

    def "wrong expiry date"() {
        given:
        CardInfo cardInfo = new CardInfo().cardNumber("123412341234").expirationDate("2-24")

        when:
        formatter.validateCardInfo(cardInfo)

        then:
        def throwable = thrown(IllegalArgumentException)

        expect:
        throwable.message == "Bad expiry date format"
    }

    def "wrong cvc2"() {
        given:
        CardInfo cardInfo = new CardInfo().cardNumber("123412341234").expirationDate("12/12").cvc2("fo")

        when:
        formatter.validateCardInfo(cardInfo)

        then:
        def throwable = thrown(IllegalArgumentException)

        expect:
        throwable.message == "Bad cvc2 format"
    }
}
