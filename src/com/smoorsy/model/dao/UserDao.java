package com.smoorsy.model.dao;

import com.smoorsy.model.entity.users_schema.User;

import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<Long, User> {
    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Optional<User> update(User entity) {
        return Optional.empty();
    }

    @Override
    public Optional<User> save(User entity) {
        return Optional.empty();
    }
}
