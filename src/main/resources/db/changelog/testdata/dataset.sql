INSERT INTO users(username, password, email, role_id) VALUES('usertest', '$2a$10$7s8i0LOTm6CRvONAMlJ9POjtUxXjvPyQXwgQfrWTzbi2lvRjNRLYK', 'user@email.com', 1);
INSERT INTO users(username, password, email, role_id) VALUES('admintest', '$2a$10$BKm6btRYxNdgyypNRvlwvOwDGcfL9Y/YSS5Cd25.S5VkIqV41iXmi', 'admin@email.com', 2);

INSERT INTO tab(label, tab_order) VALUES('Météo', 1);
INSERT INTO tab(label, tab_order) VALUES('Flux RSS', 2);
INSERT INTO tab(label, tab_order) VALUES('Agenda', 3);
INSERT INTO tab(label, tab_order) VALUES('Strava', 4);
INSERT INTO tab(label, tab_order) VALUES('Steam', 5);

INSERT INTO widget(type, data, widget_order, tab_id) VALUES(1, '{"city": "Nice"}', 1, (SELECT id FROM tab WHERE label = 'Météo'));
INSERT INTO widget(type, data, widget_order, tab_id) VALUES(2, '{"url": "https://www.jeuxvideo.com/rss/rss-pc.xml"}', 1, (SELECT id FROM tab WHERE label = 'Flux RSS'));
INSERT INTO widget(type, data, widget_order, tab_id) VALUES(3, '{"calendarUrls": []}', 1, (SELECT id FROM tab WHERE label = 'Agenda'));
INSERT INTO widget(type, data, widget_order, tab_id) VALUES(4, '{}', 1, (SELECT id FROM tab WHERE label = 'Strava'));
INSERT INTO widget(type, data, widget_order, tab_id) VALUES(5, '{}', 1, (SELECT id FROM tab WHERE label = 'Steam'));