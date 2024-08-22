CREATE SEQUENCE IF NOT EXISTS public.role_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS roles (
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('public.role_id_seq'::regclass),
    name CHARACTER VARYING
);

CREATE SEQUENCE IF NOT EXISTS public.user_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS users (
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('public.user_id_seq'::regclass),
    username CHARACTER VARYING,
    password CHARACTER VARYING,
    email CHARACTER VARYING,
    role_id INTEGER REFERENCES roles (id),
    CONSTRAINT UK_username UNIQUE (username)
);