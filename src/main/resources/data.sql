-- Insert Users
INSERT INTO User_Entity (login, password, role, email) VALUES ('userek', 'userek', 'USER', 'userek@mail.com');
INSERT INTO User_Entity (login, password, role, email) VALUES ('admin', 'admin', 'ADMIN', 'admin@mail.com');
INSERT INTO User_Entity (login, password, role, email) VALUES ('john_doe', 'password123', 'USER', 'john_doe@mail.com');
INSERT INTO User_Entity (login, password, role, email) VALUES ('jane_doe', 'password123', 'USER', 'jane_doe@mail.com');
INSERT INTO User_Entity (login, password, role, email) VALUES ('alice', 'password123', 'USER', 'alice@mail.com');
INSERT INTO User_Entity (login, password, role, email) VALUES ('bob', 'password123', 'USER', 'bob@mail.com');

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
INSERT INTO Category (name) VALUES ('Health');
INSERT INTO Category (name) VALUES ('Education');

-- Publications
INSERT INTO Publication (title, description, category_id, user_id, creation_date, moderation_state, expiration_date)
VALUES ('kebab', 'pyszny kebab', 3, 1, '2023-11-22', 'ACCEPTED', '2024-06-19 19:22');

INSERT INTO Publication (title, description, category_id, user_id, creation_date, moderation_state)
VALUES ('kebab2', 'niedobry kebab', 3, 1, '2023-11-22', 'WAITING_FOR_APPROVAL');

INSERT INTO Publication (title, description, category_id, user_id, creation_date, moderation_state, expiration_date)
VALUES ('Awesome Car', 'A great car for sale', 1, 2, '2024-01-15', 'ACCEPTED', '2024-07-15 14:30');

INSERT INTO Publication (title, description, category_id, user_id, creation_date, moderation_state, expiration_date)
VALUES ('Football Match', 'Exciting football match this weekend', 2, 3, '2024-02-20', 'ACCEPTED', '2024-08-20 16:45');

INSERT INTO Publication (title, description, category_id, user_id, creation_date, moderation_state)
VALUES ('Italian Pasta Recipe', 'Delicious homemade pasta recipe', 3, 4, '2024-03-10', 'WAITING_FOR_APPROVAL');

INSERT INTO Publication (title, description, category_id, user_id, creation_date, moderation_state, expiration_date)
VALUES ('Summer Collection', 'Latest summer fashion trends', 4, 5, '2024-04-05', 'ACCEPTED', '2024-10-05 12:00');

INSERT INTO Publication (title, description, category_id, user_id, creation_date, moderation_state)
VALUES ('Tech Conference', 'Annual tech conference in Silicon Valley', 8, 6, '2024-05-18', 'WAITING_FOR_APPROVAL');

INSERT INTO Publication (title, description, category_id, user_id, creation_date, moderation_state, expiration_date)
VALUES ('Business Strategies', 'Effective business strategies for startups', 7, 2, '2024-06-01', 'ACCEPTED', '2024-12-01 10:30');

INSERT INTO Publication (title, description, category_id, user_id, creation_date, moderation_state, expiration_date)
VALUES ('Art Exhibition', 'Contemporary art exhibition in downtown', 6, 3, '2024-07-25', 'ACCEPTED', '2025-01-25 18:00');

INSERT INTO Publication (title, description, category_id, user_id, creation_date, moderation_state)
VALUES ('New Game Release', 'Upcoming release of the latest game', 9, 4, '2024-08-30', 'WAITING_FOR_APPROVAL');

INSERT INTO Publication (title, description, category_id, user_id, creation_date, moderation_state, expiration_date)
VALUES ('Travel Guide', 'The ultimate travel guide for 2024', 5, 5, '2024-09-10', 'ACCEPTED', '2025-03-10 11:00');

INSERT INTO Publication (title, description, category_id, user_id, creation_date, moderation_state)
VALUES ('Healthy Living Tips', 'Tips for a healthier lifestyle', 10, 6, '2024-10-05', 'WAITING_FOR_APPROVAL');

INSERT INTO Publication (title, description, category_id, user_id, creation_date, moderation_state, expiration_date)
VALUES ('Online Learning', 'Best practices for online education', 11, 2, '2024-11-22', 'ACCEPTED', '2025-05-22 09:00');
