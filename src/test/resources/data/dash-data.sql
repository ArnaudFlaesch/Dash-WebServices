truncate table widget, tab cascade;

alter sequence tab_id_seq RESTART with 1;
alter sequence widget_id_seq RESTART with 1;

insert into tab (label, tab_order, user_id) values ('News', 1, (select id from users where username ='admintest'));
insert into tab (label, tab_order, user_id) values ('Météo', 1, (select id from users where username ='admintest'));
insert into widget (type, data, widget_order, tab_id) values (1, '{"city": "Paris"}', 1, 1);
insert into widget (id, type, data, widget_order, tab_id) values (55, 10, '{"incidentName": "Incident name"}', 1, 1);