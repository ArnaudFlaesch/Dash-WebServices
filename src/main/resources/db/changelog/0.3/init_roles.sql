CREATE SEQUENCE IF NOT EXISTS public.role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS roles (
    id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.role_id_seq'::regclass),
    name character varying
);

CREATE SEQUENCE IF NOT EXISTS public.user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS users (
    id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.user_id_seq'::regclass),
    username character varying,
    password character varying,
    email character varying,
    role_id integer
);

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');