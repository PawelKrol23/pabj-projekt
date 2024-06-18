
-- Insert Users
INSERT INTO User_Entity (login, password, rola,email) VALUES ('userek', 'userek', 'USER','user@mail.com');
INSERT INTO User_Entity (login, password, rola,email) VALUES ('admin', 'admin', 'ADMIN','admin@mail.com');


-- Categories
INSERT INTO Category (name) VALUES ('Automotive');
INSERT INTO Category (name) VALUES ('Sport');
INSERT INTO Category (name) VALUES ('Food');
INSERT INTO Category (name) VALUES ('Fashion');
INSERT INTO Category (name) VALUES ('Traveling');
INSERT INTO Category (name) VALUES ('Art');
INSERT INTO Category (name) VALUES ('Business');
INSERT INTO Category (name) VALUES ('Technology');
INSERT INTO Category (name) VALUES ('Games');

-- Publications
INSERT INTO Publication (title, description, category_id,user_id, creation_date, moderation_state, expiration_date)
VALUES ('kebab', 'pyszny kebab', 3, 1, '2023-11-22', 'ACCEPTED', '2024-07-01');

INSERT INTO Publication (title, description, category_id,user_id, creation_date, moderation_state)
VALUES ('kebab2', 'niedobry kebab', 3, 1, '2023-11-22', 'WAITING_FOR_APPROVAL');
