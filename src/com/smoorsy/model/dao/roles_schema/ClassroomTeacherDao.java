package com.smoorsy.model.dao.roles_schema;

import com.smoorsy.model.dao.Dao;
import com.smoorsy.model.dao.exception.DaoException;
import com.smoorsy.model.dao.users_schema.UserDao;
import com.smoorsy.model.entity.roles_schema.ClassroomTeacher;
import com.smoorsy.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ClassroomTeacherDao implements Dao<Long, ClassroomTeacher> {
    private static final ClassroomTeacherDao INSTANCE = new ClassroomTeacherDao();

    private ClassroomTeacherDao() {
    }

    public static ClassroomTeacherDao getInstance() {
        return INSTANCE;
    }

    private ClassroomTeacher builderClassroomTeacher(ResultSet resultSet, Connection connection) {
        try {
            return ClassroomTeacher.builder()
                    .user(UserDao.getInstance().findById(resultSet.getLong("user_id"), connection).get())
                    .build();
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public List<ClassroomTeacher> findAll() {
        String SQL = """
                SELECT user_id FROM roles_schema.classroom_teacher;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<ClassroomTeacher> classroomTeachers = new ArrayList<>();

            while (resultSet.next()) {
                classroomTeachers.add(builderClassroomTeacher(resultSet, connection));
            }

            return classroomTeachers;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<ClassroomTeacher> findById(Long key) {
        try (Connection connection = ConnectionManager.get()) {
            return findById(key, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<ClassroomTeacher> findById(Long key, Connection connection) {
        String SQL = """
                SELECT user_id FROM roles_schema.classroom_teacher WHERE user_id = ?;
                """;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            ClassroomTeacher classroomTeachers = null;

            if (resultSet.next()) {
                classroomTeachers = builderClassroomTeacher(resultSet, connection);
            }

            return Optional.ofNullable(classroomTeachers);

        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public boolean delete(Long key) {
        String SQL = """
                DELETE FROM roles_schema.classroom_teacher WHERE user_id = ?;
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
    public Optional<ClassroomTeacher> insert(ClassroomTeacher entity) {
        String SQL = """
                INSERT INTO roles_schema.classroom_teacher (user_id) VALUES (?);
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setLong(1, entity.getUser().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            ClassroomTeacher classroomTeacher = null;

            if (generatedKeys.next()) {
                classroomTeacher = builderClassroomTeacher(generatedKeys, connection);
            }

            return Optional.ofNullable(classroomTeacher);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
