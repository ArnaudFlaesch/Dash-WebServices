-- Database structure and widgets config

CREATE SEQUENCE IF NOT EXISTS public.tab_id_seq
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

CREATE SEQUENCE IF NOT EXISTS public.widget_id_seq
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
);

