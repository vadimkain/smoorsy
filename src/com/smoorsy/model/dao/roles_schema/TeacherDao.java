package com.smoorsy.model.dao.roles_schema;

import com.smoorsy.model.dao.Dao;
import com.smoorsy.model.dao.exception.DaoException;
import com.smoorsy.model.dao.users_schema.UserDao;
import com.smoorsy.model.entity.roles_schema.Teacher;
import com.smoorsy.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class TeacherDao implements Dao<Long, Teacher> {
    private static final TeacherDao INSTANCE = new TeacherDao();

    private TeacherDao() {
    }

    public static TeacherDao getInstance() {
        return INSTANCE;
    }

    private Teacher builderTeacher(ResultSet resultSet, Connection connection) {
        try {
            return Teacher.builder()
                    .user(UserDao.getInstance().findById(resultSet.getLong("user_id"), connection).get())
                    .build();
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public List<Teacher> findAll() {
        String SQL = """
                SELECT user_id FROM roles_schema.teacher;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Teacher> teachers = new ArrayList<>();

            while (resultSet.next()) {
                teachers.add(builderTeacher(resultSet, connection));
            }

            return teachers;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Teacher> findById(Long key) {
        try (Connection connection = ConnectionManager.get()) {
            return findById(key, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Teacher> findById(Long key, Connection connection) {
        String SQL = """
                SELECT user_id FROM roles_schema.teacher WHERE user_id = ?;
                """;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            Teacher teacher = null;

            if (resultSet.next()) {
                teacher = builderTeacher(resultSet, connection);
            }

            return Optional.ofNullable(teacher);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long key) {
        String SQL = """
                DELETE FROM roles_schema.teacher WHERE user_id = ?;
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
    public Optional<Teacher> insert(Teacher entity) {
        String SQL = """
                INSERT INTO roles_schema.teacher (user_id) VALUES (?);
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setLong(1, entity.getUser().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            Teacher teacher = null;

            if (generatedKeys.next()) {
                teacher = builderTeacher(generatedKeys, connection);
            }

            return Optional.ofNullable(teacher);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
