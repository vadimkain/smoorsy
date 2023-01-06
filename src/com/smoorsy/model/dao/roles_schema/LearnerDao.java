package com.smoorsy.model.dao.roles_schema;

import com.smoorsy.model.dao.Dao;
import com.smoorsy.model.entity.roles_schema.Learner;

import java.util.List;
import java.util.Optional;

public class LearnerDao implements Dao<Long, Learner> {
    @Override
    public List<Learner> findAll() {
        return Dao.super.findAll();
    }

    @Override
    public Optional<Learner> findById(Long key) {
        return Dao.super.findById(key);
    }

    @Override
    public boolean delete(Long key) {
        return Dao.super.delete(key);
    }

    @Override
    public Optional<Learner> update(Learner entity) {
        return Dao.super.update(entity);
    }

    @Override
    public Optional<Learner> insert(Learner entity) {
        return Dao.super.insert(entity);
    }
}
