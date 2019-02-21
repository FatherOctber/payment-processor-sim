package com.fatheroctober.ppsim.tokenizer.crypter;

import com.fatheroctober.ppsim.common.model.CipherAlgorithm;
import com.fatheroctober.ppsim.common.model.KeyInfo;
import com.google.common.base.Throwables;
import lombok.SneakyThrows;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Key;

public class Crypter {
    private final Key key;
    private final CipherAlgorithm algo;
    private final String keyWord;

    @SneakyThrows(IOException.class)
    public Crypter(String keyWord, CipherAlgorithm algorithm) {
        final byte[] keyVal = new BASE64Decoder().decodeBuffer(keyWord);
        this.key = new SecretKeySpec(keyVal, algorithm.toString());
        this.algo = algorithm;
        this.keyWord = keyWord;
    }

    public String encrypt(String source) {
        try {
            final Cipher c = Cipher.getInstance(algo.toString());
            c.init(Cipher.ENCRYPT_MODE, key);
            final byte[] encValue = c.doFinal(source.getBytes());
            return new BASE64Encoder().encode(encValue);
        } catch (Exception e) {
            Throwables.propagate(e);
        }
        return null;
    }

    public String decrypt(String source) {
        try {
            final Cipher c = Cipher.getInstance(algo.toString());
            c.init(Cipher.DECRYPT_MODE, key);
            final byte[] decorVal = new BASE64Decoder().decodeBuffer(source);
            final byte[] decValue = c.doFinal(decorVal);
            return new String(decValue);
        } catch (Exception e) {
            Throwables.propagate(e);
        }
        return null;
    }

    public KeyInfo keyInfo() {
        return new KeyInfo(keyWord, algo);
    }
}
