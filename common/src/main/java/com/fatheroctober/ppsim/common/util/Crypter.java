package com.fatheroctober.ppsim.common.util;

import com.fatheroctober.ppsim.common.model.CipherAlgorithm;
import com.fatheroctober.ppsim.common.model.KeyInfo;
import com.google.common.base.Throwables;
import lombok.SneakyThrows;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Crypter {
    private final Key key;
    private final CipherAlgorithm algo;

    @SneakyThrows(NoSuchAlgorithmException.class)
    public Crypter(CipherAlgorithm algorithm) {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm.toString());
        keyGenerator.init(128);
        this.key = keyGenerator.generateKey();
        this.algo = algorithm;
    }

    public Crypter(Key key) {
        this.key = key;
        this.algo = CipherAlgorithm.forCode(key.getAlgorithm());
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
        return new KeyInfo(key);
    }
}
