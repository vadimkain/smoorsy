package com.smoorsy.model.dao.organization_schema;

import com.smoorsy.model.dao.Dao;
import com.smoorsy.model.dao.exception.DaoException;
import com.smoorsy.model.dao.process_schema.LessonDao;
import com.smoorsy.model.dao.roles_schema.TeacherDao;
import com.smoorsy.model.entity.organization_schema.Lesson_Teacher_Class;
import com.smoorsy.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LessonTeacherClassDao implements Dao<Long, Lesson_Teacher_Class> {
    private static final LessonTeacherClassDao INSTANCE = new LessonTeacherClassDao();

    private LessonTeacherClassDao() {
    }

    public static LessonTeacherClassDao getInstance() {
        return INSTANCE;
    }

    private Lesson_Teacher_Class builderLessonTeacherClass(ResultSet resultSet, Connection connection) {
        try {
            return Lesson_Teacher_Class.builder()
                    .id(resultSet.getLong("id"))
                    .teacher(TeacherDao.getInstance().findById(resultSet.getLong("teacher_id"), connection).get())
                    .aClass(aClassDao.getInstance().findById(resultSet.getLong("class_id"), connection).get())
                    .lesson(LessonDao.getInstance().findById(resultSet.getLong("lesson_id"), connection).get())
                    .build();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Lesson_Teacher_Class> findAll() {
        String SQL = """
                SELECT id, teacher_id, class_id, lesson_id FROM organization_schema.lessons_teachers_classes;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Lesson_Teacher_Class> lessonTeacherClasses = new ArrayList<>();

            if (resultSet.next()) {
                lessonTeacherClasses.add(builderLessonTeacherClass(resultSet, connection));
            }

            return lessonTeacherClasses;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Lesson_Teacher_Class> findById(Long key) {
        try (Connection connection = ConnectionManager.get()) {
            return findById(key, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Lesson_Teacher_Class> findById(Long key, Connection connection) {
        String SQL = """
                SELECT id, teacher_id, class_id, lesson_id FROM organization_schema.lessons_teachers_classes WHERE id = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL);) {
            preparedStatement.setLong(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            Lesson_Teacher_Class lessonTeacherClass = null;

            if (resultSet.next()) {
                lessonTeacherClass = builderLessonTeacherClass(resultSet, connection);
            }

            return Optional.ofNullable(lessonTeacherClass);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long key) {
        String SQL = """
                DELETE FROM organization_schema.lessons_teachers_classes WHERE id = ?;
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
    public Optional<Lesson_Teacher_Class> update(Lesson_Teacher_Class entity) {
        String SQL = """
                UPDATE organization_schema.lessons_teachers_classes SET teacher_id = ?, class_id = ?, lesson_id = ? WHERE id = ?;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setLong(1, entity.getTeacher().getUser().getId());
            preparedStatement.setLong(2, entity.getAClass().getId());
            preparedStatement.setLong(3, entity.getLesson().getId());
            preparedStatement.setLong(4, entity.getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            Lesson_Teacher_Class lessonTeacherClass = null;

            if (generatedKeys.next()) {
                lessonTeacherClass = builderLessonTeacherClass(generatedKeys, connection);
            }

            return Optional.ofNullable(lessonTeacherClass);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Lesson_Teacher_Class> insert(Lesson_Teacher_Class entity) {
        String SQL = """
                INSERT INTO organization_schema.lessons_teachers_classes (teacher_id, class_id, lesson_id) VALUES (?, ?, ?);
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, entity.getTeacher().getUser().getId());
            preparedStatement.setLong(2, entity.getAClass().getId());
            preparedStatement.setLong(3, entity.getLesson().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            Lesson_Teacher_Class lessonTeacherClass = null;

            if (generatedKeys.next()) {
                lessonTeacherClass = builderLessonTeacherClass(generatedKeys, connection);
            }

            return Optional.ofNullable(lessonTeacherClass);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
