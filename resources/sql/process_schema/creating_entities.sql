CREATE TABLE IF NOT EXISTS process_schema.homeworks
(
    lesson_teacher_class SERIAL REFERENCES organization_schema.lessons_teachers_classes (id) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    /* TODO: исправить говнокод с датой */
    "date"               TIMESTAMP DEFAULT CURRENT_DATE                                                                          NOT NULL,
    "temp"               TIMESTAMP                                                                                               NOT NULL,
    description          varchar(600)                                                                                            NOT NULL,
    file                 TEXT
);

CREATE TABLE IF NOT EXISTS process_schema.schedules
(
    lesson_teacher_class SERIAL REFERENCES organization_schema.lessons_teachers_classes (id) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    /* TODO: разобраться, как сделать календарь в postgres. Сейчас используется простой подход. */
    day_of_week          DATE                                                                                                    NOT NULL,
    time_begin           TIME                                                                                                    NOT NULL,
    time_end             TIME CHECK ( time_begin < time_end )                                                                    NOT NULL,
    rest_time            TIME                                                                                                    NOT NULL,
    CONSTRAINT UC_schedules UNIQUE (lesson_teacher_class, time_begin, time_end, day_of_week)
);

CREATE TABLE IF NOT EXISTS process_schema.grade_diary
(
    lesson_teacher_class SERIAL REFERENCES organization_schema.lessons_teachers_classes (id) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    "date"               DATE DEFAULT CURRENT_DATE                                                                               NOT NULL,
    grade                SMALLINT CHECK ( 1 <= grade_diary.grade AND grade_diary.grade <= 12 )                                   NOT NULL,
    learner_id           SERIAL REFERENCES roles_schema.learner (user_id) ON DELETE CASCADE ON UPDATE CASCADE                    NOT NULL
);