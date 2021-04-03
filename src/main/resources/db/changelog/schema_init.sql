-- Database structure and widgets config

CREATE SEQUENCE IF NOT EXISTS public.tab_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.tab (
    id SERIAL PRIMARY KEY NOT NULL,
    label character varying,
    tab_order integer
);

CREATE SEQUENCE IF NOT EXISTS public.widget_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.widget (
    id SERIAL PRIMARY KEY NOT NULL,
    type integer,
    data jsonb,
    widget_order integer,
    tab_id integer
);

