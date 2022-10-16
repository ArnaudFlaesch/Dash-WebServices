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

-- Database structure and widgets config

CREATE SEQUENCE IF NOT EXISTS public.widget_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE widget_type (
    id INT NOT NULL PRIMARY KEY DEFAULT nextval('public.widget_type_id_seq'::regclass),
    description CHAR(50),
    config jsonb
);

INSERT INTO widget_type (description, config) VALUES ('WEATHER', '["weather_api_key", "city"]');
INSERT INTO widget_type (description, config) VALUES ('RSS', '["url"]');
INSERT INTO widget_type (description, config) VALUES ('CALENDAR', '[{"calendars": "list"}]');
INSERT INTO widget_type (description, config) VALUES ('STRAVA', '["clientId", "clientSecret"]');
INSERT INTO widget_type (description, config) VALUES ('STEAM', '["steamUserId"]');

CREATE SEQUENCE IF NOT EXISTS public.tab_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.tab (
    id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.tab_id_seq'::regclass),
    label character varying,
    tab_order integer,
    user_id integer REFERENCES users (id)
);

CREATE SEQUENCE IF NOT EXISTS public.widget_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.widget (
    id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.widget_id_seq'::regclass),
    type integer REFERENCES widget_type (id),
    data jsonb,
    widget_order integer,
    tab_id integer REFERENCES tab (id)
);