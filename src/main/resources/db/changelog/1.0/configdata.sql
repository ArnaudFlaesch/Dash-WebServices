INSERT INTO public.widget_type (description, config) VALUES ('WEATHER', '["weather_api_key", "city"]');
INSERT INTO public.widget_type (description, config) VALUES ('RSS', '["url"]');
INSERT INTO public.widget_type (description, config) VALUES ('CALENDAR', '[{"calendars": "list"}]');
INSERT INTO public.widget_type (description, config) VALUES ('STRAVA', '["clientId", "clientSecret"]');
INSERT INTO public.widget_type (description, config) VALUES ('STEAM', '["steamUserId"]');
INSERT INTO public.widget_type (description, config) VALUES ('WORKOUT', '{}');
INSERT INTO public.widget_type (description, config) VALUES ('AIRPARIF', '["airParifApiKey", "communeInseeCode"]');
INSERT INTO public.widget_type (description, config) VALUES ('TWITTER', '["twitterHandle"]');
INSERT INTO public.widget_type (description, config) VALUES ('ECOWATT', '[]');

INSERT INTO public.mini_widget_type (description, config) VALUES ('WEATHER', '["weather_api_key", "city"]');

INSERT INTO public.roles(name) values('ROLE_USER');
INSERT INTO public.roles(name) values('ROLE_ADMIN');