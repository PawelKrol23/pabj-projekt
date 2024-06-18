
-- Insert Users
INSERT INTO User_Entity (login, password, rola,email) VALUES ('userek', 'userek', 'USER','user@mail.com');
INSERT INTO User_Entity (login, password, rola,email) VALUES ('admin', 'admin', 'ADMIN','admin@mail.com');


-- Categories
INSERT INTO Category (name) VALUES ('motoryzacja');
INSERT INTO Category (name) VALUES ('sport');
INSERT INTO Category (name) VALUES ('jedzenie');
INSERT INTO Category (name) VALUES ('moda');
INSERT INTO Category (name) VALUES ('podróże');
INSERT INTO Category (name) VALUES ('sztuka');
INSERT INTO Category (name) VALUES ('biznes');
INSERT INTO Category (name) VALUES ('technologia');
INSERT INTO Category (name) VALUES ('gry');
INSERT INTO Category (name) VALUES ('dom');

-- Publications
INSERT INTO Publication (title, description, category_id,user_id, creation_date)
VALUES ('kebab', 'pyszny kebab', 3, 1, '2023-11-22');
