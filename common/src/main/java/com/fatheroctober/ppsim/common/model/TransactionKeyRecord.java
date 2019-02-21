package com.fatheroctober.ppsim.common.model;

import lombok.Value;

@Value
public class TransactionKeyRecord {
    KeyInfo key;
    Transaction transaction;
}
