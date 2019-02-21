package com.fatheroctober.ppsim.common.persistence.operation;

import com.fatheroctober.ppsim.common.model.TransactionKeyRecord;

public interface PushDecryptionKey {
    void push(TransactionKeyRecord keyRecord);
}
