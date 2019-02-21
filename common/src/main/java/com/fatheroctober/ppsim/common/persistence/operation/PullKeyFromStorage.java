package com.fatheroctober.ppsim.common.persistence.operation;

import com.fatheroctober.ppsim.common.model.KeyInfo;
import com.fatheroctober.ppsim.common.model.Transaction;

public interface PullKeyFromStorage {
    KeyInfo pull(Transaction transaction);
}
