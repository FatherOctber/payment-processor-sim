package com.fatheroctober.ppsim.tokenizer.config;

import com.fatheroctober.ppsim.common.model.CipherAlgorithm;
import com.fatheroctober.ppsim.tokenizer.crypter.Crypter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TokenizerProperties.class)
public class TokenizerConfig {
    private final TokenizerProperties properties;

    public TokenizerConfig(TokenizerProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Crypter crypter() {
        return new Crypter(properties.getKeyWord(), CipherAlgorithm.forCode(properties.getCipherAlgorithm()));
    }

    @Bean
    @Qualifier("tokenizer-inputTopic")
    public String inputTopic() {
        return properties.getInputTopic();
    }

    @Bean
    @Qualifier("tokenizer-outputTopic")
    public String outputTopic() {
        return properties.getOutputTopic();
    }
}
