package com.fatheroctober.ppsim.tokenizer;

import com.fatheroctober.ppsim.common.model.Token;
import com.fatheroctober.ppsim.common.model.TokenizationBlock;
import com.fatheroctober.ppsim.common.util.Crypter;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class TokenizerService implements ITokenizerService {
    private final Crypter crypter;

    public TokenizerService(Crypter crypter) {
        this.crypter = crypter;
    }

    @Override
    public TokenizationBlock generateToken(String sourceMsg) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(sourceMsg), "source must contains data");
        return new TokenizationBlock(crypter.keyInfo(), new Token(crypter.encrypt(sourceMsg)));
    }
}
