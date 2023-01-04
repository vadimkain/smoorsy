CREATE TABLE IF NOT EXISTS roles_schema.learner
(
    user_id SERIAL REFERENCES users_schema.users (id) ON DELETE CASCADE NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS roles_schema.teacher
(
    user_id SERIAL REFERENCES users_schema.users (id) ON DELETE CASCADE NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS roles_schema.classroom_teacher
(
    user_id SERIAL REFERENCES users_schema.users (id) ON DELETE CASCADE NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS roles_schema.manager
(
    user_id SERIAL REFERENCES users_schema.users (id) ON DELETE CASCADE NOT NULL UNIQUE
);
