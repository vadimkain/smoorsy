package com.smoorsy.model.dao.roles_schema;

import com.smoorsy.model.dao.Dao;
import com.smoorsy.model.dao.exception.DaoException;
import com.smoorsy.model.dao.users_schema.UserDao;
import com.smoorsy.model.entity.roles_schema.Learner;
import com.smoorsy.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class LearnerDao implements Dao<Long, Learner> {
    private static final LearnerDao INSTANCE = new LearnerDao();

    private LearnerDao() {
    }

    public static LearnerDao getInstance() {
        return INSTANCE;
    }

    private Learner builderLearner(ResultSet resultSet, Connection connection) {
        try {
            return Learner.builder()
                    .user(UserDao.getInstance().findById(resultSet.getLong("user_id"), connection).get())
                    .build();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Learner> findAll() {
        String SQL = """
                SELECT user_id FROM roles_schema.learner
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Learner> learners = new ArrayList<>();

            while (resultSet.next()) {
                learners.add(builderLearner(resultSet, connection));
            }

            return learners;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Learner> findById(Long key) {
        try (Connection connection = ConnectionManager.get()) {
            return findById(key, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Learner> findById(Long key, Connection connection) {
        String SQL = """
                SELECT user_id FROM roles_schema.learner WHERE user_id = ?;
                """;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            Learner learner = null;

            if (resultSet.next()) {
                learner = builderLearner(resultSet, connection);
            }

            return Optional.ofNullable(learner);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long key) {
        String SQL = """
                DELETE FROM roles_schema.learner WHERE user_id = ?;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, key);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Learner> insert(Learner entity) {
        String SQL = """
                INSERT INTO roles_schema.learner (user_id) VALUES (?);
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setLong(1, entity.getUser().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            Learner learner = null;

            if (generatedKeys.next()) {
                learner = builderLearner(generatedKeys, connection);
            }

            return Optional.ofNullable(learner);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
