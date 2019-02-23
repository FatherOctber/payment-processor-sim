package com.fatheroctober.ppsim.common.util

import com.fatheroctober.ppsim.common.model.CipherAlgorithm
import spock.lang.Specification

class CrypterTest extends Specification {

    def "encrypt and decrypt successfully"() {
        given:
        def crypter = new Crypter(CipherAlgorithm.AES)

        when:
        def encryption = crypter.encrypt("Some word")
        def res = crypter.decrypt(encryption)

        then:
        noExceptionThrown()

        expect:
        res == "Some word"
    }
}
