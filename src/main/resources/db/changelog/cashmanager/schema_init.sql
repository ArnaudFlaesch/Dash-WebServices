CREATE SEQUENCE IF NOT EXISTS public.label_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.label (
    id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.label_id_seq'::regclass),
    label character varying
);

CREATE SEQUENCE IF NOT EXISTS public.expense_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.expense (
    id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.expense_id_seq'::regclass),
    amount integer,
    expense_date date,
    isRegular boolean DEFAULT false,
    label_id integer REFERENCES label (id)
);