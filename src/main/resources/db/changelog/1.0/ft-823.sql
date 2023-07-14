-- FT 823 Incident Widget

insert into widget_type (description, config) values ('INCIDENT', '["incidentName"]');

create sequence IF NOT EXISTS public.incident_id_seq
    AS integer
    START with 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table if not exists public.incident (
    id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.incident_id_seq'::regclass),
    incident_name character varying,
    last_incident_date date,
    widget_id integer REFERENCES widget (id)
);

create sequence IF NOT EXISTS public.incident_streak_id_seq
    AS integer
    START with 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table if not exists public.incident_streak (
    id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.incident_streak_id_seq'::regclass),
    streak_start_date date,
    streak_end_date date,
    incident_id integer REFERENCES incident (id)
);