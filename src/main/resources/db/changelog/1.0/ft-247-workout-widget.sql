INSERT INTO widget_type (description, config) VALUES ('WORKOUT', '{}');

CREATE SEQUENCE IF NOT EXISTS public.workout_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.workout_type (
    id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.workout_type_id_seq'::regclass),
    name character varying
);

CREATE SEQUENCE IF NOT EXISTS public.workout_session_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.workout_session (
    id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.workout_session_id_seq'::regclass),
    workout_date timestamp
);

CREATE TABLE IF NOT EXISTS public.workout_exercise (
    workout_session_id integer REFERENCES public.workout_session (id),
    workout_type_id integer REFERENCES public.workout_type (id),
    number_of_reps integer NOT NULL DEFAULT 0
);