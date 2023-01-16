package com.smoorsy.model.dao.roles_schema;

import com.smoorsy.model.dao.Dao;
import com.smoorsy.model.dao.exception.DaoException;
import com.smoorsy.model.dao.users_schema.UserDao;
import com.smoorsy.model.entity.roles_schema.Developer;
import com.smoorsy.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class DeveloperDao implements Dao<Long, Developer> {
    private static final DeveloperDao INSTANCE = new DeveloperDao();

    private DeveloperDao() {
    }

    public static DeveloperDao getInstance() {
        return INSTANCE;
    }

    private Developer builderDeveloper(ResultSet resultSet, Connection connection) {
        try {
            return Developer.builder()
                    .user(UserDao.getInstance().findById(resultSet.getLong("user_id"), connection).get())
                    .build();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Developer> findAll() {
        String SQL = """
                SELECT user_id FROM roles_schema.developer;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Developer> Developers = new ArrayList<>();

            while (resultSet.next()) {
                Developers.add(builderDeveloper(resultSet, connection));
            }

            return Developers;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Developer> findById(Long key) {
        try (Connection connection = ConnectionManager.get()) {
            return findById(key, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Developer> findById(Long key, Connection connection) {
        String SQL = """
                SELECT user_id FROM roles_schema.developer WHERE user_id = ?;
                """;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            Developer Developers = null;

            if (resultSet.next()) {
                Developers = builderDeveloper(resultSet, connection);
            }

            return Optional.ofNullable(Developers);

        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public boolean delete(Long key) {
        String SQL = """
                DELETE FROM roles_schema.developer WHERE user_id = ?;
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
    public Optional<Developer> insert(Developer entity) {
        String SQL = """
                INSERT INTO roles_schema.developer (user_id) VALUES (?);
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setLong(1, entity.getUser().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            Developer Developer = null;

            if (generatedKeys.next()) {
                Developer = builderDeveloper(generatedKeys, connection);
            }

            return Optional.ofNullable(Developer);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
