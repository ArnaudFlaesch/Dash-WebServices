INSERT INTO PUBLIC.widget_type (description, config) VALUES ('WEATHER', '["weather_api_key", "city"]');
INSERT INTO PUBLIC.widget_type (description, config) VALUES ('RSS', '["url"]');
INSERT INTO PUBLIC.widget_type (description, config) VALUES ('CALENDAR', '[{"calendars": "list"}]');
INSERT INTO PUBLIC.widget_type (description, config) VALUES ('STRAVA', '["clientId", "clientSecret"]');
INSERT INTO PUBLIC.widget_type (description, config) VALUES ('STEAM', '["steamUserId"]');
INSERT INTO PUBLIC.widget_type (description, config) VALUES ('WORKOUT', '{}');
INSERT INTO PUBLIC.widget_type (description, config) VALUES ('AIRPARIF', '["airParifApiKey", "communeInseeCode"]');
INSERT INTO PUBLIC.widget_type (description, config) VALUES ('TWITTER', '["twitterHandle"]');
INSERT INTO PUBLIC.widget_type (description, config) VALUES ('ECOWATT', '[]');

INSERT INTO PUBLIC.mini_widget_type (description, config) VALUES ('WEATHER', '["weather_api_key", "city"]');

INSERT INTO PUBLIC.roles (name) VALUES('ROLE_USER');
INSERT INTO PUBLIC.roles (name) VALUES('ROLE_ADMIN');