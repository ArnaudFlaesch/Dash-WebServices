-- Database structure and widgets config

CREATE SEQUENCE IF NOT EXISTS PUBLIC.widget_type_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE widget_type (
    id INT NOT NULL PRIMARY KEY DEFAULT nextval('PUBLIC.widget_type_id_seq'::regclass),
    description CHAR(50),
    config jsonb
);

CREATE SEQUENCE IF NOT EXISTS PUBLIC.mini_widget_type_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE mini_widget_type (
    id INT NOT NULL PRIMARY KEY DEFAULT nextval('PUBLIC.mini_widget_type_id_seq'::regclass),
    description CHAR(50),
    config jsonb
);

CREATE SEQUENCE IF NOT EXISTS PUBLIC.tab_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS PUBLIC.tab (
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PUBLIC.tab_id_seq'::regclass),
    label CHARACTER VARYING,
    tab_order INTEGER,
    user_id INTEGER REFERENCES users (id)
);

CREATE SEQUENCE IF NOT EXISTS PUBLIC.widget_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS PUBLIC.widget (
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PUBLIC.widget_id_seq'::regclass),
    type INTEGER REFERENCES widget_type (id),
    data jsonb,
    widget_order INTEGER,
    tab_id INTEGER REFERENCES tab (id)
);

CREATE SEQUENCE IF NOT EXISTS PUBLIC.mini_widget_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS PUBLIC.mini_widget (
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PUBLIC.mini_widget_id_seq'::regclass),
    type INTEGER REFERENCES mini_widget_type (id),
    data jsonb,
    user_id INTEGER REFERENCES users (id)
);

-- Workout Widget

CREATE SEQUENCE IF NOT EXISTS PUBLIC.workout_type_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS PUBLIC.workout_type (
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PUBLIC.workout_type_id_seq'::regclass),
    name CHARACTER VARYING,
    user_id INTEGER REFERENCES users (id)
);

CREATE SEQUENCE IF NOT EXISTS PUBLIC.workout_session_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS PUBLIC.workout_session (
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PUBLIC.workout_session_id_seq'::regclass),
    workout_date timestamp,
    user_id INTEGER REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS PUBLIC.workout_exercise (
    workout_session_id INTEGER REFERENCES PUBLIC.workout_session (id),
    workout_type_id INTEGER REFERENCES PUBLIC.workout_type (id),
    number_of_reps INTEGER NOT NULL DEFAULT 0
);

-- Twitter Widget

CREATE SEQUENCE IF NOT EXISTS PUBLIC.followed_user_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS PUBLIC.followed_user (
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PUBLIC.followed_user_id_seq'::regclass),
    user_handle CHARACTER VARYING,
    user_id INTEGER REFERENCES users (id)
);

-- Notifications

CREATE SEQUENCE IF NOT EXISTS PUBLIC.notification_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS PUBLIC.notification (
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PUBLIC.notification_id_seq'::regclass),
    message CHARACTER VARYING,
    notification_date timestamptz,
    notification_type CHARACTER VARYING,
    is_read boolean DEFAULT false
);