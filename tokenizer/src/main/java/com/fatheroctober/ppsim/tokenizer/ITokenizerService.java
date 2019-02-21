package com.fatheroctober.ppsim.tokenizer;

import com.fatheroctober.ppsim.common.model.KeyInfo;
import com.fatheroctober.ppsim.common.model.Token;
import org.apache.commons.lang3.tuple.Pair;

public interface ITokenizerService {
    Pair<KeyInfo, Token> generateToken(String sourceMsg);
}
