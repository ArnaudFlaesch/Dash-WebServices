CREATE TABLE IF NOT EXISTS roles (
    id integer NOT NULL,
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
    id integer NOT NULL DEFAULT nextval('public.user_id_seq'::regclass),
    username character varying,
    password character varying,
    email character varying,
    role_id integer
);

INSERT INTO roles(id, name) VALUES(1, 'ROLE_USER');
INSERT INTO roles(id, name) VALUES(2, 'ROLE_ADMIN');

INSERT INTO users(username, password, email, role_id) VALUES('aflaesch', '$2a$10$DzYNkYSoNkDh2/zy3NDJSOWRNi70FQAFdiHxAZHzNlhR6XYcvCGDC', 'arnaud.flaesch93@gmail.com', 2);

