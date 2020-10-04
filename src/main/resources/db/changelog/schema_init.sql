-- Database structure and widgets config

CREATE SEQUENCE public.tab_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.tab (
    id integer NOT NULL DEFAULT nextval('public.tab_id_seq'::regclass),
    label character varying,
    tab_order integer
);

CREATE SEQUENCE public.widget_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.widget (
    id integer NOT NULL DEFAULT nextval('public.widget_id_seq'::regclass),
    type integer,
    data jsonb,
    widget_order integer,
    tab_id integer
	widget_type_id integer
);

ALTER TABLE ONLY public.widget ADD CONSTRAINT tab_id_foreign FOREIGN KEY (id) REFERENCES public.tab(id) NOT VALID;

CREATE SEQUENCE public.widget_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE widget_type (
    id INT PRIMARY KEY NOT NULL DEFAULT nextval('public.widget_type_id_seq'::regclass),
    description CHAR(50),
    config jsonb
);

INSERT INTO widget_type (description, config) VALUES ('WEATHER', '["weather_api_key", "city"]');
INSERT INTO widget_type (description, config) VALUES ('RSS', '["url"]');
INSERT INTO widget_type (description, config) VALUES ('CALENDAR', '[{"calendars": "list"}]');
INSERT INTO widget_type (description, config) VALUES ('STRAVA', '["clientId", "clientSecret"]');