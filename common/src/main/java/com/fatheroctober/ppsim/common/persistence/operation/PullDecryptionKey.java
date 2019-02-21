package com.fatheroctober.ppsim.common.persistence.operation;

import com.fatheroctober.ppsim.common.model.KeyInfo;
import com.fatheroctober.ppsim.common.model.Transaction;

public interface PullDecryptionKey {
    KeyInfo pull(Transaction transaction);
}
