package com.fatheroctober.ppsim.common.persistence;

import java.util.Optional;

public interface Dao<V> {
    /**
     * Get entity from storage
     *
     * @param id
     * @return object
     */
    Optional<V> get(Long id);

    /**
     * Save entity object in storage
     *
     * @param value - object
     * @return id
     */
    Long save(V value);
}
