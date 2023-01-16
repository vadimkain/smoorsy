package com.smoorsy.model.dao.users_schema;

import com.smoorsy.model.dao.Dao;
import com.smoorsy.model.dao.exception.DaoException;
import com.smoorsy.model.entity.users_schema.Role;
import com.smoorsy.model.entity.users_schema.User;
import com.smoorsy.model.entity.users_schema.Users_and_Roles;
import com.smoorsy.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.*;

public class Users_and_RolesDao implements Dao<User, Users_and_Roles> {

    private static final Users_and_RolesDao INSTANCE = new Users_and_RolesDao();

    private Users_and_RolesDao() {
    }

    public static Users_and_RolesDao getInstance() {
        return INSTANCE;
    }

    private Users_and_Roles builderUsers_and_Roles(ResultSet resultSet, Connection connection) {
        try {
            return Users_and_Roles.builder()
                    .user(UserDao.getInstance().findById(resultSet.getLong("user_id"), connection).get())
                    .role(Role.valueOf(resultSet.getString("role_name")))
                    .build();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Users_and_Roles> findAll() {
        String SQL = """
                SELECT user_id, role_name FROM users_schema.users_and_roles;
                """;

        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Users_and_Roles> usersAndRoles = new ArrayList<>();

            while (resultSet.next()) {
                usersAndRoles.add(builderUsers_and_Roles(resultSet, connection));
            }

            return usersAndRoles;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Users_and_Roles> findAllByRole(Role role) {
        String SQL = """
                SELECT user_id, role_name FROM users_schema.users_and_roles WHERE role_name = CAST(? AS users_schema.roles);
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setString(1, role.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Users_and_Roles> usersAndRoles = new ArrayList<>();

            while (resultSet.next()) {
                usersAndRoles.add(builderUsers_and_Roles(resultSet, connection));
            }

            return usersAndRoles;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Users_and_Roles> findAllByUser(User user) {
        try (
                Connection connection = ConnectionManager.get();
        ) {

            return findAllByUser(user, connection);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Users_and_Roles> findAllByUser(User user, Connection connection) {
        String SQL = """
                SELECT user_id, role_name FROM users_schema.users_and_roles WHERE user_id = ?;
                """;

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ) {
            preparedStatement.setLong(1, user.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Users_and_Roles> usersAndRoles = new ArrayList<>();

            while (resultSet.next()) {
                usersAndRoles.add(builderUsers_and_Roles(resultSet, connection));
            }

            return usersAndRoles;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean deleteUserByRole(User user, Role role) {
        String SQL = """
                DELETE FROM users_schema.users_and_roles WHERE user_id = ? AND role_name = CAST(? AS users_schema.roles);
                """;

        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, role.toString());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Users_and_Roles> insert(Users_and_Roles entity) {
        String SQL = """
                INSERT INTO users_schema.users_and_roles (user_id, role_name) VALUES (?, CAST(? AS users_schema.roles));
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setLong(1, entity.getUser().getId());
            preparedStatement.setObject(2, entity.getRole().toString());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            Users_and_Roles usersAndRoles = null;

            if (generatedKeys.next()) {
                usersAndRoles = builderUsers_and_Roles(generatedKeys, connection);
            }

            return Optional.ofNullable(usersAndRoles);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
