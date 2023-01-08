package com.smoorsy.model.dao.process_schema;

import com.smoorsy.model.dao.Dao;
import com.smoorsy.model.dao.exception.DaoException;
import com.smoorsy.model.dao.organization_schema.LessonTeacherClassDao;
import com.smoorsy.model.entity.organization_schema.Lesson_Teacher_Class;
import com.smoorsy.model.entity.process_schema.Homework;
import com.smoorsy.utils.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.*;

public class HomeworkDao implements Dao<Lesson_Teacher_Class, Homework> {
    private static final HomeworkDao INSTANCE = new HomeworkDao();

    private HomeworkDao() {
    }

    public static HomeworkDao getInstance() {
        return INSTANCE;
    }

    private Homework builderHomework(ResultSet resultSet, Connection connection) {
        try {
            return Homework.builder()
                    .lessonTeacherClass(LessonTeacherClassDao.getInstance().findById(resultSet.getLong("id"), connection).get())
                    .date(resultSet.getObject("date", LocalDate.class))
                    .temp(resultSet.getObject("temp", LocalDate.class))
                    .description(resultSet.getString("description"))
                    .file(resultSet.getString("file"))
                    .build();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Homework> findAll() {
        String SQL = """
                SELECT lesson_teacher_class, date, temp, description, file FROM process_schema.homeworks;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Homework> homeworks = new ArrayList<>();

            if (resultSet.next()) {
                homeworks.add(builderHomework(resultSet, connection));
            }

            return homeworks;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Homework> findById(Lesson_Teacher_Class key) {
        String SQL = """
                SELECT lesson_teacher_class, date, temp, description, file FROM process_schema.homeworks WHERE lesson_teacher_class = ?
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, key.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            Homework homework = null;

            if (resultSet.next()) {
                homework = builderHomework(resultSet, connection);
            }

            return Optional.ofNullable(homework);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Lesson_Teacher_Class key) {
        String SQL = """
                DELETE FROM process_schema.homeworks WHERE lesson_teacher_class = ?;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, key.getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Homework> update(Homework entity) {
        String SQL = """
                UPDATE process_schema.homeworks SET date = ?, temp = ?, description = ?, file = ? WHERE lesson_teacher_class = ?;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setObject(1, entity.getDate());
            preparedStatement.setObject(2, entity.getTemp());
            preparedStatement.setString(3, entity.getDescription());
            preparedStatement.setString(4, entity.getFile());
            preparedStatement.setLong(5, entity.getLessonTeacherClass().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            Homework homework = null;

            if (generatedKeys.next()) {
                homework = builderHomework(generatedKeys, connection);
            }

            return Optional.ofNullable(homework);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Homework> insert(Homework entity) {
        String SQL = """
                INSERT INTO process_schema.homeworks (lesson_teacher_class, date, temp, description, file) VALUES (?, ?, ?, ?, ?);
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, entity.getLessonTeacherClass().getId());
            preparedStatement.setObject(2, entity.getDate());
            preparedStatement.setObject(3, entity.getTemp());
            preparedStatement.setString(4, entity.getDescription());
            preparedStatement.setString(5, entity.getFile());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            Homework homework = null;

            if (generatedKeys.next()) {
                homework = builderHomework(generatedKeys, connection);
            }

            return Optional.ofNullable(homework);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
