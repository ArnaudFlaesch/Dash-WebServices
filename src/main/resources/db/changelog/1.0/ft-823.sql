-- FT 823 Incident Widget

INSERT INTO PUBLIC.widget_type (description, config) VALUES ('INCIDENT', '["incidentName"]');

create sequence IF NOT EXISTS PUBLIC.incident_id_seq
    AS INTEGER
    START with 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table if not exists PUBLIC.incident (
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PUBLIC.incident_id_seq'::regclass),
    last_incident_date date,
    widget_id INTEGER REFERENCES widget (id)
);

create sequence IF NOT EXISTS PUBLIC.incident_streak_id_seq
    AS INTEGER
    START with 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table if not exists PUBLIC.incident_streak (
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PUBLIC.incident_streak_id_seq'::regclass),
    streak_start_date date,
    streak_end_date date,
    incident_id INTEGER REFERENCES incident (id)
);