INSERT INTO users(username, password, email, role_id) VALUES('usertest', '$2a$10$EovHPEXDpFNDFek8ERvTdufGypWTDJF6OoFGHYU.e1XOyqiuzH2fe', 'user@email.com', 1);
INSERT INTO users(username, password, email, role_id) VALUES('admintest', '$2a$10$EovHPEXDpFNDFek8ERvTduFaJxwsWI1KCIFER/B3TvSXEebbCLxFG', 'admin@email.com', 2);

INSERT INTO tab(label, tab_order, user_id) VALUES('Home', 1, (SELECT id FROM users WHERE username = 'admintest'));