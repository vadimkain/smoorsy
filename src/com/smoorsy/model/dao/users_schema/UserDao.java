package com.smoorsy.model.dao.users_schema;

import com.smoorsy.model.dao.Dao;
import com.smoorsy.model.dao.exception.DaoException;
import com.smoorsy.model.entity.users_schema.User;
import com.smoorsy.utils.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.*;

public class UserDao implements Dao<Long, User> {
    private static final UserDao INSTANCE = new UserDao();

    private UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    private User buildUser(ResultSet resultSet) {
        try {
            return User.builder()
                    .id(resultSet.getLong("id"))
                    .surname(resultSet.getString("surname"))
                    .name(resultSet.getString("name"))
                    .patronymic(resultSet.getString("patronymic"))
                    .birthday(LocalDate.from(resultSet.getTimestamp("birthday").toLocalDateTime()))
                    .email(resultSet.getString("email"))
                    .password(resultSet.getString("password"))
                    .build();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        String SQL = """
                SELECT id, surname, name, patronymic, birthday, email, password FROM users_schema.users WHERE email = ? AND password = ?;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            User user = null;

            if (resultSet.next()) {
                user = buildUser(resultSet);
            }

            return Optional.ofNullable(user);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> findAll() {
        String SQL = """
                SELECT id, surname, name, patronymic, birthday, email, password FROM users_schema.users;
                """;

        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }

            return users;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findById(Long key) {
        try (
                Connection connection = ConnectionManager.get();
        ) {
            return findById(key, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<User> findById(Long id, Connection connection) {
        String SQL = """
                SELECT id, surname, name, patronymic, birthday, email, password FROM users_schema.users WHERE id = ?;
                """;

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            User user = null;

            if (resultSet.next()) {
                user = buildUser(resultSet);
            }

            return Optional.ofNullable(user);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long key) {
        String SQL = """
                DELETE FROM users_schema.users WHERE id = ?;
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
    public Optional<User> update(User entity) {
        String SQL = """
                UPDATE users_schema.users SET surname = ?, name = ?, patronymic = ?, birthday = ?, email = ?, password = ? WHERE id = ?;
                """;

        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {

            preparedStatement.setString(1, entity.getSurname());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getPatronymic());
            preparedStatement.setDate(4, Date.valueOf(entity.getBirthday()));
            preparedStatement.setString(5, entity.getEmail());
            preparedStatement.setString(6, entity.getPassword());
            preparedStatement.setLong(7, entity.getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            User user = null;

            if (generatedKeys.next()) {
                user = buildUser(generatedKeys);
            }

            return Optional.ofNullable(user);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> insert(User entity) {
        String SQL = """
                INSERT INTO users_schema.users (surname, name, patronymic, birthday, email, password) 
                VALUES (?, ?, ?, ?, ?, ?); 
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, entity.getSurname());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getPatronymic());
            preparedStatement.setDate(4, Date.valueOf(entity.getBirthday()));
            preparedStatement.setString(5, entity.getEmail());
            preparedStatement.setString(6, entity.getPassword());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong("id"));
            }

            return Optional.ofNullable(entity);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
