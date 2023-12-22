CREATE SEQUENCE IF NOT EXISTS public.label_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.label (
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('public.label_id_seq'::regclass),
    label CHARACTER VARYING,
    user_id INTEGER REFERENCES users (id),
    CONSTRAINT UK_label UNIQUE (label)
);

CREATE SEQUENCE IF NOT EXISTS public.expense_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.expense (
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('public.expense_id_seq'::regclass),
    amount real,
    expense_date date,
    isRegular boolean DEFAULT false,
    label_id INTEGER REFERENCES label (id)
);