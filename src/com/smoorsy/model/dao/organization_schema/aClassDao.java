package com.smoorsy.model.dao.organization_schema;

import com.smoorsy.model.dao.Dao;
import com.smoorsy.model.dao.exception.DaoException;
import com.smoorsy.model.dao.roles_schema.ClassroomTeacherDao;
import com.smoorsy.model.entity.organization_schema.aClass;
import com.smoorsy.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.*;

public class aClassDao implements Dao<Long, aClass> {
    private static final aClassDao INSTANCE = new aClassDao();

    private aClassDao() {
    }

    public static aClassDao getInstance() {
        return INSTANCE;
    }

    private aClass builderClass(ResultSet resultSet, Connection connection) {
        try {
            return aClass.builder()
                    .id(resultSet.getLong("id"))
                    .school(SchoolDao.getInstance().findById(resultSet.getLong("school_id"), connection).get())
                    .classroomTeacher(ClassroomTeacherDao.getInstance().findById(resultSet.getLong("classroom_teacher"), connection).get())
                    .title(resultSet.getString("title"))
                    .build();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<aClass> findAll() {
        String SQL = """
                SELECT id, school_id, classroom_teacher_id, title FROM organization_schema.classes;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<aClass> classes = new ArrayList<>();

            while (resultSet.next()) {
                classes.add(builderClass(resultSet, connection));
            }

            return classes;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<aClass> findById(Long key) {
        try (Connection connection = ConnectionManager.get()) {
            return findById(key, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<aClass> findById(Long key, Connection connection) {
        String SQL = """
                SELECT id, school_id, classroom_teacher_id, title FROM organization_schema.classes WHERE id = ?;
                """;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            aClass aclass = null;

            if (resultSet.next()) {
                aclass = builderClass(resultSet, connection);
            }

            return Optional.ofNullable(aclass);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long key) {
        String SQL = """
                DELETE FROM organization_schema.classes WHERE id = ?; 
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
    public Optional<aClass> update(aClass entity) {
        String SQL = """
                UPDATE organization_schema.classes SET school_id = ?, classroom_teacher_id = ?, title = ? WHERE id = ?;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setLong(1, entity.getSchool().getId());
            preparedStatement.setLong(2, entity.getClassroomTeacher().getUser().getId());
            preparedStatement.setString(3, entity.getTitle());
            preparedStatement.setLong(3, entity.getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            aClass aclass = null;

            if (generatedKeys.next()) {
                aclass = builderClass(generatedKeys, connection);
            }

            return Optional.ofNullable(aclass);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<aClass> insert(aClass entity) {
        String SQL = """
                INSERT INTO organization_schema.classes (school_id, classroom_teacher_id, title) VALUES (?, ?, ?);
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, entity.getSchool().getId());
            preparedStatement.setLong(2, entity.getClassroomTeacher().getUser().getId());
            preparedStatement.setString(3, entity.getTitle());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            aClass aclass = null;

            if (generatedKeys.next()) {
                aclass = builderClass(generatedKeys, connection);
            }

            return Optional.ofNullable(aclass);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
