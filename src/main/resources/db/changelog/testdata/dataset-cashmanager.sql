INSERT INTO label(label) VALUES('Courses');
INSERT INTO label(label) VALUES('Restaurant');

INSERT INTO expense(amount, label_id, isRegular, expense_date) VALUES(100, 1, 'FALSE', '2022-04-01');
INSERT INTO expense(amount, label_id, isRegular, expense_date) VALUES(32, 1, 'FALSE', '2022-03-01');
INSERT INTO expense(amount, label_id, isRegular, expense_date) VALUES(55, 2, 'FALSE', '2022-02-01');