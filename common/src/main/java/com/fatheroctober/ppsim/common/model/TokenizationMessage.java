package com.fatheroctober.ppsim.common.model;

import lombok.Value;

@Value
public class TokenizationMessage {
    Transaction transaction;
    Token token;
}
