-- FT 823 Incident Widget

INSERT INTO PUBLIC.widget_type (description, config) VALUES ('INCIDENT', '["incidentName"]');

CREATE SEQUENCE IF NOT EXISTS PUBLIC.incident_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS PUBLIC.incident (
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PUBLIC.incident_id_seq'::regclass),
    last_incident_date DATE,
    widget_id INTEGER REFERENCES widget (id)
);

CREATE SEQUENCE IF NOT EXISTS PUBLIC.incident_streak_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS PUBLIC.incident_streak (
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PUBLIC.incident_streak_id_seq'::regclass),
    streak_start_date DATE,
    streak_end_date DATE,
    incident_id INTEGER REFERENCES incident (id)
);