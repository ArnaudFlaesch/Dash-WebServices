truncate table widget, tab cascade;

alter sequence tab_id_seq RESTART with 1;
alter sequence widget_id_seq RESTART with 1;

INSERT INTO PUBLIC.tab (label, tab_order, user_id) VALUES ('News', 1, (select id from PUBLIC.users where username ='admintest'));
INSERT INTO PUBLIC.tab (label, tab_order, user_id) VALUES ('Weather', 1, (select id from PUBLIC.users where username ='admintest'));
INSERT INTO PUBLIC.widget (type, data, widget_order, tab_id) VALUES (1, '{"city": "Paris"}', 1, 1);
INSERT INTO PUBLIC.widget (id, type, data, widget_order, tab_id) VALUES (55, 10, '{"incidentName": "Incident name"}', 1, 1);