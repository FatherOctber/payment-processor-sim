package com.fatheroctober.ppsim.tokenizer;

import com.fatheroctober.ppsim.common.model.TokenizationBlock;

public interface ITokenizerService {
    TokenizationBlock generateToken(String sourceMsg);
}
