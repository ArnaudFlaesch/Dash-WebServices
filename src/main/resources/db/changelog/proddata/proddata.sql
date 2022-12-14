insert into widget_type (description, config) values ('WEATHER', '["weather_api_key", "city"]');
insert into widget_type (description, config) values ('RSS', '["url"]');
insert into widget_type (description, config) values ('CALENDAR', '[{"calendars": "list"}]');
insert into widget_type (description, config) values ('STRAVA', '["clientId", "clientSecret"]');
insert into widget_type (description, config) values ('STEAM', '["steamUserId"]');
insert into widget_type (description, config) values ('WORKOUT', '{}');
insert into widget_type (description, config) values ('AIRPARIF', '["airParifApiKey", "communeInseeCode"]');
insert into widget_type (description, config) values ('TWITTER', '["twitterHandle"]');
insert into widget_type (description, config) values ('ECOWATT', '[]');

insert into roles(name) values('ROLE_USER');
insert into roles(name) values('ROLE_ADMIN');

insert into users(username, password, email, role_id) values('aflaesch', '$2a$10$DzYNkYSoNkDh2/zy3NDJSOWRNi70FQAFdiHxAZHzNlhR6XYcvCGDC', 'arnaud.flaesch93@gmail.com', 2);
insert into users(username, password, email, role_id) values('demo', '$2a$10$7s8i0LOTm6CRvONAMlJ9POjwYfvRVAvV4vIMPVa3QL1QM.2E24bm2', 'demo@gmail.com', 2);