INSERT INTO users(username, password, email, role_id) VALUES('usertest', '$2a$10$7s8i0LOTm6CRvONAMlJ9POjtUxXjvPyQXwgQfrWTzbi2lvRjNRLYK', 'user@email.com', 1);
INSERT INTO users(username, password, email, role_id) VALUES('admintest', '$2a$10$BKm6btRYxNdgyypNRvlwvOwDGcfL9Y/YSS5Cd25.S5VkIqV41iXmi', 'admin@email.com', 2);

INSERT INTO tab(label, tab_order, user_id) VALUES('Home', 1, (SELECT id FROM users WHERE username = 'admintest'));