package com.smoorsy.model.dao.organization_schema;

import com.smoorsy.model.dao.Dao;
import com.smoorsy.model.dao.exception.DaoException;
import com.smoorsy.model.dao.roles_schema.ManagerDao;
import com.smoorsy.model.entity.organization_schema.School;
import com.smoorsy.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.*;

public class SchoolDao implements Dao<Long, School> {
    private static final SchoolDao INSTANCE = new SchoolDao();

    private SchoolDao() {
    }

    public static SchoolDao getInstance() {
        return INSTANCE;
    }

    private School builderSchool(ResultSet resultSet, Connection connection) {
        try {
            return School.builder()
                    .id(resultSet.getLong("id"))
                    .department(DepartmentDao.getInstance().findById(resultSet.getLong("department_id"), connection).get())
                    .name(resultSet.getString("name"))
                    .manager(ManagerDao.getInstance().findById(resultSet.getLong("manager_id"), connection).get())
                    .build();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<School> findAll() {
        String SQL = """
                SELECT id, department_id, name, manager_id FROM organization_schema.schools;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<School> schools = new ArrayList<>();

            while (resultSet.next()) {
                schools.add(builderSchool(resultSet, connection));
            }

            return schools;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<School> findById(Long key) {
        try (Connection connection = ConnectionManager.get()) {
            return findById(key, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<School> findById(Long key, Connection connection) {
        String SQL = """
                SELECT id, department_id, name, manager_id FROM organization_schema.schools WHERE id = ?;
                """;

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            School school = null;

            if (resultSet.next()) {
                school = builderSchool(resultSet, connection);
            }

            return Optional.ofNullable(school);

        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public boolean delete(Long key) {
        String SQL = """
                DELETE FROM organization_schema.schools WHERE id = ?;
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

    // TODO: предусмотреть вывод ошибки ограничения внешнего ключа
    @Override
    public Optional<School> update(School entity) {
        String SQL = """
                UPDATE organization_schema.schools SET department_id = ?, name = ?, manager_id = ? WHERE id = ?;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setLong(1, entity.getDepartment().getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setLong(3, entity.getManager().getUser().getId());
            preparedStatement.setLong(4, entity.getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            School school = null;

            if (generatedKeys.next()) {
                school = builderSchool(generatedKeys, connection);
            }

            return Optional.ofNullable(school);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<School> insert(School entity) {
        String SQL = """
                INSERT INTO organization_schema.schools (department_id, name, manager_id) VALUES (?, ?, ?);
                """;

        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setLong(1, entity.getDepartment().getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setLong(3, entity.getManager().getUser().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            School school = null;

            if (generatedKeys.next()) {
                school = builderSchool(generatedKeys, connection);
            }

            return Optional.ofNullable(school);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
