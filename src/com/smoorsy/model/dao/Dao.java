package com.smoorsy.model.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, T> {
    List<T> findAll();

    Optional<T> findById(K id);

    boolean delete(K id);

    Optional<T> update(T entity);

    Optional<T> save(T entity);
}
