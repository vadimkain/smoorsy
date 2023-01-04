CREATE TABLE IF NOT EXISTS users_schema.users
(
    id         SERIAL PRIMARY KEY NOT NULL,
    surname    varchar(64)        NOT NULL,
    name       varchar(64)        NOT NULL,
    patronymic varchar(64)        NOT NULL,
    /* TODO: исправить говнокод с датой */
    birthday   DATE               CHECK ('1940-01-01' < birthday AND birthday < '2018-01-01') NOT NULL,
    email      varchar(120)       NOT NULL UNIQUE,
    password   varchar(120)       NOT NULL
    );

/* https://metanit.com/sql/postgresql/5.2.php */
CREATE TYPE users_schema.roles AS ENUM ('LEARNER', 'TEACHER', 'CLASSROOM_TEACHER', 'MANAGER');

CREATE TABLE IF NOT EXISTS users_schema.privileges
(
    id    SERIAL PRIMARY KEY NOT NULL,
    title varchar(64)        NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS users_schema.users_and_roles
(
    user_id   SERIAL REFERENCES users_schema.users (id) ON DELETE CASCADE NOT NULL,
    role_name users_schema.roles                                          NOT NULL
    );

CREATE UNIQUE INDEX IF NOT EXISTS UNIQUE_INDEX_users_and_roles
    ON users_schema.users_and_roles (user_id, role_name);

CREATE TABLE IF NOT EXISTS users_schema.roles_and_privileges
(
    role_name     users_schema.roles                                               NOT NULL,
    privileges_id SERIAL REFERENCES users_schema.privileges (id) ON DELETE CASCADE NOT NULL
    );

CREATE UNIQUE INDEX IF NOT EXISTS UNIQUE_INDEX_roles_and_privileges
    ON users_schema.roles_and_privileges (role_name, privileges_id);
