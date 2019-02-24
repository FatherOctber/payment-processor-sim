package com.fatheroctober.ppsim.consumer;

import com.fatheroctober.ppsim.common.model.CustomerMessage;

public interface ILogService {
    void log(long transaction, CustomerMessage msg);
}
