package com.fatheroctober.ppsim.common.model;

import lombok.Value;

@Value
public class TokenizationBlock {
    KeyInfo key;
    Token token;

    public String getTokenValue() {
        return token.getValue();
    }
}
