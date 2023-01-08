package com.smoorsy.model.dao.process_schema;

import com.smoorsy.model.dao.Dao;
import com.smoorsy.model.dao.exception.DaoException;
import com.smoorsy.model.dao.organization_schema.LessonTeacherClassDao;
import com.smoorsy.model.entity.organization_schema.Lesson_Teacher_Class;
import com.smoorsy.model.entity.process_schema.Schedules;
import com.smoorsy.utils.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.*;

public class SchedulesDao implements Dao<Lesson_Teacher_Class, Schedules> {
    private static final SchedulesDao INSTANCE = new SchedulesDao();

    private SchedulesDao() {
    }

    public static SchedulesDao getInstance() {
        return INSTANCE;
    }

    private Schedules builderSchedules(ResultSet resultSet, Connection connection) {
        try {
            return Schedules.builder()
                    .lessonTeacherClass(LessonTeacherClassDao.getInstance().findById(resultSet.getLong("id"), connection).get())
                    .day_of_week(resultSet.getObject("dat_of_week", LocalDate.class))
                    .time_begin(resultSet.getObject("time_begin", LocalTime.class))
                    .time_end(resultSet.getObject("time_end", LocalTime.class))
                    .rest_time(resultSet.getObject("rest_time", LocalTime.class))
                    .build();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Schedules> findAll() {
        String SQL = """
                SELECT lesson_teacher_class, day_of_week, time_begin, time_end, rest_time FROM process_schema.schedules;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Schedules> schedules = new ArrayList<>();

            while (resultSet.next()) {
                schedules.add(builderSchedules(resultSet, connection));
            }

            return schedules;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Schedules> findById(Lesson_Teacher_Class key) {
        String SQL = """
                SELECT lesson_teacher_class, day_of_week, time_begin, time_end, rest_time FROM process_schema.schedules WHERE lesson_teacher_class = ?;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, key.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            Schedules schedules = null;

            if (resultSet.next()) {
                schedules = builderSchedules(resultSet, connection);
            }

            return Optional.ofNullable(schedules);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Lesson_Teacher_Class key) {
        String SQL = """
                DELETE FROM process_schema.schedules WHERE lesson_teacher_class = ?;
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
    public Optional<Schedules> update(Schedules entity) {
        String SQL = """
                UPDATE process_schema.schedules SET day_of_week = ?, time_begin = ?, time_end = ?, rest_time = ? WHERE lesson_teacher_class = ?;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setObject(1, entity.getDay_of_week());
            preparedStatement.setObject(2, entity.getTime_begin());
            preparedStatement.setObject(3, entity.getTime_end());
            preparedStatement.setObject(4, entity.getRest_time());
            preparedStatement.setLong(5, entity.getLessonTeacherClass().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            Schedules schedules = null;

            if (generatedKeys.next()) {
                schedules = builderSchedules(generatedKeys, connection);
            }

            return Optional.ofNullable(schedules);

        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public Optional<Schedules> insert(Schedules entity) {
        String SQL = """
                INSERT INTO process_schema.schedules (lesson_teacher_class, day_of_week, time_begin, time_end, rest_time) VALUES (?, ?, ?, ?, ?); 
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setLong(1, entity.getLessonTeacherClass().getId());
            preparedStatement.setObject(2, entity.getDay_of_week());
            preparedStatement.setObject(3, entity.getTime_begin());
            preparedStatement.setObject(4, entity.getTime_end());
            preparedStatement.setObject(5, entity.getRest_time());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            Schedules schedules = null;

            if (generatedKeys.next()) {
                schedules = builderSchedules(generatedKeys, connection);
            }

            return Optional.ofNullable(schedules);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
