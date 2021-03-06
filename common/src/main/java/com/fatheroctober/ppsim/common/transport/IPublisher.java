package com.fatheroctober.ppsim.common.transport;

import org.apache.commons.lang3.tuple.Pair;

public interface IPublisher<T> {
    void publish(Pair<ILogRecord<T>, T> data);
}
