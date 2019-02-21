package com.fatheroctober.ppsim.tokenizer;

import com.fatheroctober.ppsim.common.model.KeyInfo;
import com.fatheroctober.ppsim.common.model.Token;
import com.fatheroctober.ppsim.tokenizer.crypter.Crypter;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
public class TokenizerService implements ITokenizerService {
    private final Crypter crypter;

    public TokenizerService(Crypter crypter) {
        this.crypter = crypter;
    }

    @Override
    public Pair<KeyInfo, Token> generateToken(String sourceMsg) {
        return Pair.of(crypter.keyInfo(), new Token(crypter.encrypt(sourceMsg)));
    }
}
