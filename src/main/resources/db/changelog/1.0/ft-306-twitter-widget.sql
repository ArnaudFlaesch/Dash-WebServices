INSERT INTO widget_type (description, config) VALUES ('TWITTER', '["twitterHandle"]');

CREATE SEQUENCE IF NOT EXISTS public.followed_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.followed_user (
    id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.followed_user_id_seq'::regclass),
    user_handle character varying,
    user_id integer REFERENCES users (id)
);