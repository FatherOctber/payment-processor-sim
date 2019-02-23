package com.fatheroctober.ppsim.common.model

import com.fatheroctober.ppsim.common.util.Crypter
import spock.lang.Specification

class KeyInfoTest extends Specification {

    def "serialize and deserialize successfully"() {
        given:
        def key = new Crypter(CipherAlgorithm.AES).keyInfo()

        when:
        def serealized = key.serializedContent()
        def res = KeyInfo.create(serealized)

        then:
        noExceptionThrown()

        expect:
        res == key
    }
}
