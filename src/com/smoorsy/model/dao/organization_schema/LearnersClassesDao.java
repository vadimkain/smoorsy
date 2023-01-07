package com.smoorsy.model.dao.organization_schema;

import com.smoorsy.model.dao.Dao;
import com.smoorsy.model.dao.exception.DaoException;
import com.smoorsy.model.dao.roles_schema.LearnerDao;
import com.smoorsy.model.entity.organization_schema.Learner_and_Class;
import com.smoorsy.model.entity.organization_schema.aClass;
import com.smoorsy.model.entity.roles_schema.Learner;
import com.smoorsy.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.*;

public class LearnersClassesDao implements Dao<Learner, Learner_and_Class> {
    private static final LearnersClassesDao INSTANCE = new LearnersClassesDao();

    private LearnersClassesDao() {
    }

    public static LearnersClassesDao getInstance() {
        return INSTANCE;
    }

    private Learner_and_Class builderLearnerClasses(ResultSet resultSet, Connection connection) {
        try {
            return Learner_and_Class.builder()
                    .learner(LearnerDao.getInstance().findById(resultSet.getLong("learner_id"), connection).get())
                    .aClass(aClassDao.getInstance().findById(resultSet.getLong("class_id"), connection).get())
                    .build();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Learner_and_Class> findAll() {
        String SQL = """
                SELECT learner_id, class_id FROM organization_schema.learners_and_classes;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Learner_and_Class> learnerAndClasses = new ArrayList<>();

            while (resultSet.next()) {
                learnerAndClasses.add(builderLearnerClasses(resultSet, connection));
            }

            return learnerAndClasses;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Learner_and_Class> findByLearner(Learner key) {
        try (Connection connection = ConnectionManager.get()) {
            return findByLearner(key, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Learner_and_Class> findByLearner(Learner key, Connection connection) {
        String SQL = """
                SELECT learner_id, class_id FROM organization_schema.learners_and_classes WHERE learner_id = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setLong(1, key.getUser().getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            Learner_and_Class learnerAndClass = null;

            if (resultSet.next()) {
                learnerAndClass = builderLearnerClasses(resultSet, connection);
            }

            return Optional.ofNullable(learnerAndClass);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Learner_and_Class> findByClass(aClass key) {
        try (Connection connection = ConnectionManager.get()) {
            return findByClass(key, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Learner_and_Class> findByClass(aClass key, Connection connection) {
        String SQL = """
                SELECT learner_id, class_id FROM organization_schema.learners_and_classes WHERE class_id = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setLong(1, key.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            Learner_and_Class learnerAndClass = null;

            if (resultSet.next()) {
                learnerAndClass = builderLearnerClasses(resultSet, connection);
            }

            return Optional.ofNullable(learnerAndClass);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean deleteByLearner(Learner key) {
        try (Connection connection = ConnectionManager.get()) {
            return deleteByLearner(key, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean deleteByLearner(Learner key, Connection connection) {
        String SQL = """
                DELETE FROM organization_schema.learners_and_classes WHERE learner_id = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setLong(1, key.getUser().getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    public boolean deleteByClass(aClass key) {
        try (Connection connection = ConnectionManager.get()) {
            return deleteByClass(key, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean deleteByClass(aClass key, Connection connection) {
        String SQL = """
                DELETE FROM organization_schema.learners_and_classes WHERE learner_id = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setLong(1, key.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Learner_and_Class> updateClassForLearner(Learner_and_Class entity) {
        String SQL = """
                UPDATE organization_schema.learners_and_classes SET class_id = ? WHERE learner_id = ?;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setLong(1, entity.getAClass().getId());
            preparedStatement.setLong(2, entity.getLearner().getUser().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            Learner_and_Class learnerAndClass = null;

            if (generatedKeys.next()) {
                learnerAndClass = builderLearnerClasses(generatedKeys, connection);
            }

            return Optional.ofNullable(learnerAndClass);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Learner_and_Class> insert(Learner_and_Class entity) {
        String SQL = """
                INSERT INTO organization_schema.learners_and_classes (learner_id, class_id) VALUES (?, ?);
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setLong(1, entity.getAClass().getId());
            preparedStatement.setLong(2, entity.getLearner().getUser().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            Learner_and_Class learnerAndClass = null;

            if (generatedKeys.next()) {
                learnerAndClass = builderLearnerClasses(generatedKeys, connection);
            }

            return Optional.ofNullable(learnerAndClass);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
