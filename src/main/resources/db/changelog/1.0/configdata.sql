insert into widget_type (description, config) values ('WEATHER', '["weather_api_key", "city"]');
insert into widget_type (description, config) values ('RSS', '["url"]');
insert into widget_type (description, config) values ('CALENDAR', '[{"calendars": "list"}]');
insert into widget_type (description, config) values ('STRAVA', '["clientId", "clientSecret"]');
insert into widget_type (description, config) values ('STEAM', '["steamUserId"]');
insert into widget_type (description, config) values ('WORKOUT', '{}');
insert into widget_type (description, config) values ('AIRPARIF', '["airParifApiKey", "communeInseeCode"]');
insert into widget_type (description, config) values ('TWITTER', '["twitterHandle"]');
insert into widget_type (description, config) values ('ECOWATT', '[]');

insert into mini_widget_type (description, config) values ('WEATHER', '["weather_api_key", "city"]');

insert into roles(name) values('ROLE_USER');
insert into roles(name) values('ROLE_ADMIN');