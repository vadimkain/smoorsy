package com.smoorsy.model.dao.process_schema;

import com.smoorsy.model.dao.Dao;
import com.smoorsy.model.dao.exception.DaoException;
import com.smoorsy.model.entity.process_schema.Lesson;
import com.smoorsy.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class LessonDao implements Dao<Long, Lesson> {
    private static final LessonDao INSTANCE = new LessonDao();

    private LessonDao() {
    }

    public static LessonDao getInstance() {
        return INSTANCE;
    }

    private Lesson builderLesson(ResultSet resultSet) {
        try {
            return Lesson.builder()
                    .id(resultSet.getLong("id"))
                    .title(resultSet.getString("title"))
                    .build();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Lesson> findAll() {
        String SQL = """
                SELECT id, title FROM process_schema.lessons;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Lesson> lessons = new ArrayList<>();

            if (resultSet.next()) {
                lessons.add(builderLesson(resultSet));
            }

            return lessons;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Lesson> findById(Long key) {
        try (Connection connection = ConnectionManager.get()) {
            return findById(key, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Lesson> findById(Long key, Connection connection) {
        String SQL = """
                SELECT id, title FROM process_schema.lessons WHERE id = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setLong(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            Lesson lesson = null;

            if (resultSet.next()) {
                lesson = builderLesson(resultSet);
            }

            return Optional.ofNullable(lesson);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long key) {
        String SQL = """
                DELETE FROM process_schema.lessons WHERE id = ?;
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
    public Optional<Lesson> update(Lesson entity) {
        String SQL = """
                UPDATE process_schema.lessons SET title = ? WHERE id = ?;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setLong(2, entity.getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            Lesson lesson = null;

            if (generatedKeys.next()) {
                lesson = builderLesson(generatedKeys);
            }

            return Optional.ofNullable(lesson);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Lesson> insert(Lesson entity) {
        String SQL = """
                INSERT INTO process_schema.lessons (title) VALUES (?);
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, entity.getTitle());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            Lesson lesson = null;

            if (generatedKeys.next()) {
                lesson = builderLesson(generatedKeys);
            }

            return Optional.ofNullable(lesson);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
