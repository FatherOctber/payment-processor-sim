package com.fatheroctober.ppsim.common.model;

public enum CipherAlgorithm {
    AES("AES"),
    DES("DES");

    private String algo;

    CipherAlgorithm(String algo) {
        this.algo = algo;
    }

    @Override
    public String toString() {
        return algo;
    }

    public static CipherAlgorithm forCode(String code) {
        for (CipherAlgorithm alg : values()) {
            if (alg.algo.equals(code)) {
                return alg;
            }
        }
        throw new RuntimeException("Crypto algorithm not matched");
    }
}
