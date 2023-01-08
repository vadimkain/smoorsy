package com.smoorsy.model.dao.process_schema;

import com.smoorsy.model.dao.Dao;
import com.smoorsy.model.dao.exception.DaoException;
import com.smoorsy.model.dao.organization_schema.LessonTeacherClassDao;
import com.smoorsy.model.dao.roles_schema.LearnerDao;
import com.smoorsy.model.entity.process_schema.GradeDiary;
import com.smoorsy.model.entity.roles_schema.Learner;
import com.smoorsy.utils.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class GradeDiaryDao implements Dao<Learner, GradeDiary> {
    private static final GradeDiaryDao INSTANCE = new GradeDiaryDao();

    private GradeDiaryDao() {
    }

    public static GradeDiaryDao getInstance() {
        return INSTANCE;
    }

    private GradeDiary builderGradeDiary(ResultSet resultSet, Connection connection) {
        try {
            return GradeDiary.builder()
                    .learner(LearnerDao.getInstance().findById(resultSet.getLong("learner_id"), connection).get())
                    .lessonTeacherClass(LessonTeacherClassDao.getInstance().findById(resultSet.getLong("lesson_teacher_class"), connection).get())
                    .date(resultSet.getObject("date", LocalDate.class))
                    .grade(resultSet.getShort("grade"))
                    .build();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<GradeDiary> findAll() {
        String SQL = """
                SELECT lesson_teacher_class, date, grade, learner_id FROM process_schema.grade_diary;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<GradeDiary> gradeDiaries = new ArrayList<>();

            while (resultSet.next()) {
                gradeDiaries.add(builderGradeDiary(resultSet, connection));
            }

            return gradeDiaries;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<GradeDiary> findById(Learner key) {
        String SQL = """
                SELECT lesson_teacher_class, date, grade, learner_id FROM process_schema.grade_diary WHERE learner_id = ?;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, key.getUser().getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            GradeDiary gradeDiary = null;

            if (resultSet.next()) {
                gradeDiary = builderGradeDiary(resultSet, connection);
            }

            return Optional.ofNullable(gradeDiary);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Learner key) {
        String SQL = """
                DELETE FROM process_schema.grade_diary WHERE learner_id = ?;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, key.getUser().getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<GradeDiary> update(GradeDiary entity) {
        String SQL = """
                UPDATE process_schema.grade_diary SET date = ?, grade = ? WHERE learner_id = ? AND lesson_teacher_class = ?;
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setObject(1, entity.getDate());
            preparedStatement.setShort(2, entity.getGrade());
            preparedStatement.setLong(3, entity.getLearner().getUser().getId());
            preparedStatement.setLong(4, entity.getLessonTeacherClass().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            GradeDiary gradeDiary = null;

            if (generatedKeys.next()) {
                gradeDiary = builderGradeDiary(generatedKeys, connection);
            }

            return Optional.ofNullable(gradeDiary);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<GradeDiary> insert(GradeDiary entity) {
        String SQL = """
                INSERT INTO process_schema.grade_diary (lesson_teacher_class, date, grade, learner_id) VALUES (?, ?, ?, ?);
                """;
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setLong(1, entity.getLessonTeacherClass().getId());
            preparedStatement.setObject(2, entity.getDate());
            preparedStatement.setShort(3, entity.getGrade());
            preparedStatement.setLong(4, entity.getLearner().getUser().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            GradeDiary gradeDiary = null;

            if (generatedKeys.next()) {
                gradeDiary = builderGradeDiary(generatedKeys, connection);
            }

            return Optional.ofNullable(gradeDiary);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
