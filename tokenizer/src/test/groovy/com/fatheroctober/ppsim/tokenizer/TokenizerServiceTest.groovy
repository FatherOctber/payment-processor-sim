package com.fatheroctober.ppsim.tokenizer

import com.fatheroctober.ppsim.common.model.KeyInfo
import com.fatheroctober.ppsim.common.model.Token
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [TokenizerServiceTestConfig.class])
class TokenizerServiceTest extends Specification {
    @Autowired
    ITokenizerService tokenizer

    def "tokenize successfully"() {
        given:
        def someStr = 'some important data'

        when:
        def tokenizationBlock = tokenizer.generateToken(someStr)

        then:
        noExceptionThrown()

        expect:
        tokenizationBlock.getKey() != null && tokenizationBlock.getKey() instanceof KeyInfo
        tokenizationBlock.getToken() != null && tokenizationBlock.getToken() instanceof Token
    }

    def "empty source exception"() {
        when:
        tokenizer.generateToken(null)

        then:
        thrown(IllegalArgumentException.class)
    }
}
