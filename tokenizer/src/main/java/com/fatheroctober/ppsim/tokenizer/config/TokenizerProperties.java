package com.fatheroctober.ppsim.tokenizer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("tokenizer")
public class TokenizerProperties {
    @Getter
    @Setter
    private String cipherAlgorithm;

    @Getter
    @Setter
    private String inputTopic;

    @Getter
    @Setter
    private String outputTopic;

}
