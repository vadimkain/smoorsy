package com.smoorsy.model.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, T> {
    default List<T> findAll() {
        return null;
    }

    default Optional<T> findById(K key) {
        return Optional.empty();
    }

    default boolean delete(K key) {
        return false;
    }

    default Optional<T> update(T entity) {
        return Optional.empty();
    }

    default Optional<T> insert(T entity) {
        return Optional.empty();
    }
}
