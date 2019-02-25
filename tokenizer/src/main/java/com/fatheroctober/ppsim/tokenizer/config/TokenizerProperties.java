package com.fatheroctober.ppsim.tokenizer.config;

import com.fatheroctober.ppsim.common.model.CipherAlgorithm;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@ConfigurationProperties("tokenizer")
public class TokenizerProperties {
    @Getter
    @Setter
    private String cipherAlgorithm;

    @Getter
    @Setter
    private String kafkaInputTopic;

    @Getter
    @Setter
    private String kafkaOutputTopic;

    @Getter
    @Setter
    private int partitions;

    @PostConstruct
    public void validation() {
        Preconditions.checkNotNull(CipherAlgorithm.forCode(cipherAlgorithm), "cipherAlgorithm must be defined. Possible values "
                + Arrays.toString(CipherAlgorithm.values()));
        Preconditions.checkArgument(partitions > 0, "partitions must be > 0");
        Preconditions.checkArgument(StringUtils.isNotEmpty(kafkaInputTopic), "kafkaInputTopic must be defined");
        Preconditions.checkArgument(StringUtils.isNotEmpty(kafkaOutputTopic), "kafkaOutputTopic must be defined");
    }

}
