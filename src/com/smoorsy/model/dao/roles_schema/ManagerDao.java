package com.smoorsy.model.dao.roles_schema;

import com.smoorsy.model.dao.Dao;
import com.smoorsy.model.dao.exception.DaoException;
import com.smoorsy.model.dao.users_schema.UserDao;
import com.smoorsy.model.entity.roles_schema.ClassroomTeacher;
import com.smoorsy.model.entity.roles_schema.Manager;
import com.smoorsy.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ManagerDao implements Dao<Long, Manager> {
    private static final ManagerDao INSTANCE = new ManagerDao();

    private ManagerDao() {
    }

    public static ManagerDao getInstance() {
        return INSTANCE;
    }

    private Manager builderManager(ResultSet resultSet, Connection connection) {
        try {
            return Manager.builder()
                    .user(UserDao.getInstance().findById(resultSet.getLong("user_id"), connection).get())
                    .build();
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public List<Manager> findAll() {
        String SQL = """
                SELECT user_id FROM roles_schema.manager;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Manager> managers = new ArrayList<>();

            while (resultSet.next()) {
                managers.add(builderManager(resultSet, connection));
            }

            return managers;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Manager> findById(Long key) {
        String SQL = """
                SELECT user_id FROM roles_schema.manager WHERE user_id = ?;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            Manager manager = null;

            if (resultSet.next()) {
                manager = builderManager(resultSet, connection);
            }

            return Optional.ofNullable(manager);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long key) {
        String SQL = """
                DELETE FROM roles_schema.manager WHERE user_id = ?;
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
    public Optional<Manager> insert(Manager entity) {
        String SQL = """
                INSERT INTO roles_schema.manager (user_id) VALUES (?);
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setLong(1, entity.getUser().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            Manager manager = null;

            if (generatedKeys.next()) {
                manager = builderManager(generatedKeys, connection);
            }

            return Optional.ofNullable(manager);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
