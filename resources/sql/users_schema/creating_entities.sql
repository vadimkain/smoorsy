CREATE SCHEMA IF NOT EXISTS users_schema;
CREATE TABLE IF NOT EXISTS users_schema.users
(
    id         SERIAL PRIMARY KEY                                               NOT NULL,
    surname    varchar(64)                                                      NOT NULL,
    name       varchar(64)                                                      NOT NULL,
    patronymic varchar(64)                                                      NOT NULL,
    birthday   DATE CHECK ('1940-01-01' < birthday AND birthday < '2018-01-01') NOT NULL,
    email      varchar(120) UNIQUE                                              NOT NULL,
    password   varchar(120)                                                     NOT NULL
);

CREATE TYPE users_schema.roles AS ENUM ('LEARNER', 'TEACHER', 'CLASSROOM_TEACHER', 'MANAGER', 'DEVELOPER');

CREATE TABLE IF NOT EXISTS users_schema.users_and_roles
(
    user_id   SERIAL REFERENCES users_schema.users (id) ON DELETE CASCADE NOT NULL,
    role_name users_schema.roles                                          NOT NULL,
    CONSTRAINT UC_users_and_roles UNIQUE (user_id, role_name)
);

CREATE UNIQUE INDEX IF NOT EXISTS UNIQUE_INDEX_users_and_roles
    ON users_schema.users_and_roles (user_id, role_name);