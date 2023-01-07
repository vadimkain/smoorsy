package com.smoorsy.model.dao.organization_schema;

import com.smoorsy.model.dao.Dao;
import com.smoorsy.model.dao.exception.DaoException;
import com.smoorsy.model.entity.organization_schema.Department;
import com.smoorsy.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.*;

public class DepartmentDao implements Dao<Long, Department> {
    private static final DepartmentDao INSTANCE = new DepartmentDao();

    private DepartmentDao() {
    }

    public static DepartmentDao getInstance() {
        return INSTANCE;
    }

    private Department builderDepartment(ResultSet resultSet) {
        try {
            return Department.builder()
                    .id(resultSet.getLong("id"))
                    .city(resultSet.getString("city"))
                    .build();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Department> findAll() {
        String SQL = """
                SELECT id, city FROM organization_schema.departments;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Department> departments = new ArrayList<>();

            while (resultSet.next()) {
                departments.add(builderDepartment(resultSet));
            }

            return departments;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Department> findById(Long key) {
        try (Connection connection = ConnectionManager.get()) {
            return findById(key, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Department> findById(Long key, Connection connection) {
        String SQL = """
                SELECT id, city FROM organization_schema.departments WHERE id = ?;
                """;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            Department department = null;

            if (resultSet.next()) {
                department = builderDepartment(resultSet);
            }

            return Optional.ofNullable(department);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long key) {
        String SQL = """
                DELETE FROM organization_schema.departments WHERE id = ?;
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
    public Optional<Department> update(Department entity) {
        String SQL = """
                UPDATE organization_schema.departments SET city = ? WHERE id = ?;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, entity.getCity());
            preparedStatement.setLong(2, entity.getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            Department department = null;

            if (generatedKeys.next()) {
                department = builderDepartment(generatedKeys);
            }

            return Optional.ofNullable(department);


        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Department> insert(Department entity) {
        String SQL = """
                INSERT INTO organization_schema.departments (city) VALUES (?);
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, entity.getCity());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            Department department = null;

            if (generatedKeys.next()) {
                department = builderDepartment(generatedKeys);
            }

            return Optional.ofNullable(department);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
