TRUNCATE TABLE expense, label;

ALTER SEQUENCE label_id_seq RESTART WITH 1;
ALTER SEQUENCE expense_id_seq RESTART WITH 1;

INSERT INTO PUBLIC.label (label, user_id) VALUES ('Courses', (SELECT id from PUBLIC.users WHERE username = 'admintest'));
INSERT INTO PUBLIC.label (label, user_id) VALUES ('Restaurant', (SELECT id from PUBLIC.users WHERE username = 'admintest'));

INSERT INTO PUBLIC.expense (amount, label_id, isRegular, expense_date) VALUES (100, (SELECT id from PUBLIC.label WHERE label = 'Courses'), 'FALSE', '2022-04-01');
INSERT INTO PUBLIC.expense (amount, label_id, isRegular, expense_date) VALUES (32, (SELECT id from PUBLIC.label WHERE label = 'Courses'), 'FALSE', '2022-03-01');
INSERT INTO PUBLIC.expense (amount, label_id, isRegular, expense_date) VALUES (55, (SELECT id from PUBLIC.label WHERE label = 'Restaurant'), 'FALSE', '2022-02-01');