package com.fatheroctober.ppsim.tokenizer

import com.fatheroctober.ppsim.common.model.CipherAlgorithm
import com.fatheroctober.ppsim.common.util.Crypter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TokenizerServiceTestConfig {

    @Bean
    Crypter crypter() {
        new Crypter(CipherAlgorithm.AES)
    }

    @Bean
    ITokenizerService customerService() {
        new TokenizerService(crypter())
    }
}
