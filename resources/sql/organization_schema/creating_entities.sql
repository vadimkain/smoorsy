CREATE TABLE IF NOT EXISTS organization_schema.departments
(
    id   SERIAL PRIMARY KEY NOT NULL,
    city varchar(64)        NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS organization_schema.schools
(
    id            SERIAL PRIMARY KEY                                                       NOT NULL,
    department_id SERIAL REFERENCES organization_schema.departments (id) ON DELETE CASCADE NOT NULL,
    "name"        varchar(64)                                                              NOT NULL,
    manager_id    SERIAL REFERENCES roles_schema.manager (user_id) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT UC_school UNIQUE (department_id, "name")
);

CREATE UNIQUE INDEX IF NOT EXISTS UNIQUE_INDEX_schools
    ON organization_schema.schools (department_id, "name");

CREATE TABLE IF NOT EXISTS organization_schema.classes
(
    id                   SERIAL PRIMARY KEY                                                                     NOT NULL,
    school_id            SERIAL REFERENCES organization_schema.schools (id) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    classroom_teacher_id SERIAL REFERENCES roles_schema.classroom_teacher (user_id) ON DELETE SET NULL ON UPDATE CASCADE,
    title                varchar(16)                                                                            NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS UNIQUE_INDEX_classes
    ON organization_schema.classes (school_id, classroom_teacher_id, title);

CREATE TABLE IF NOT EXISTS organization_schema.learners_and_classes
(
    learner_id SERIAL REFERENCES roles_schema.learner (user_id) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    class_id   SERIAL REFERENCES organization_schema.classes (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UC_learners_and_classes UNIQUE (learner_id, class_id)
);

CREATE TABLE IF NOT EXISTS process_schema.lessons
(
    id    SERIAL PRIMARY KEY NOT NULL,
    title varchar(64) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS organization_schema.lessons_teachers_classes
(
    id         SERIAL PRIMARY KEY                                                 NOT NULL,
    teacher_id SERIAL REFERENCES roles_schema.teacher (user_id) ON DELETE CASCADE NOT NULL,
    class_id   SERIAL REFERENCES organization_schema.classes (id) ON DELETE SET NULL ON UPDATE CASCADE,
    lesson_id  SERIAL REFERENCES process_schema.lessons (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UC_lessons_teachers_classes UNIQUE (class_id, lesson_id)
);