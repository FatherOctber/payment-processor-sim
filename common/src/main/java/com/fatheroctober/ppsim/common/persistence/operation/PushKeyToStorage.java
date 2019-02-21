package com.fatheroctober.ppsim.common.persistence.operation;

import com.fatheroctober.ppsim.common.model.TransactionKeyRecord;

public interface PushKeyToStorage {
    void push(TransactionKeyRecord keyRecord);
}
