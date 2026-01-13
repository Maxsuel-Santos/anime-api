-- Códios (auxiliares) SQL para inserir dados na tabela "animes" e resetar o contador de IDs. Esse projeto usa PostgreSQL, mas pode ser adaptado para outros SGBDs.

-- ----------------------------------------------
-- Resetar o contador de IDs da tabela "animes"
-- ----------------------------------------------
TRUNCATE TABLE animes RESTART IDENTITY; 

-- ----------------------------------------------
-- Verificação dos dados antes da inserção 
-- ----------------------------------------------
SELECT * FROM animes;

-- ----------------------------------------------
-- Visualização dos usuários cadastrados 
-- ----------------------------------------------
SELECT * FROM users;

-- ----------------------------------------------
-- Inserção de dados na tabela "animes"
-- ----------------------------------------------
INSERT INTO animes (name) VALUES ('Naruto Shippuden');
INSERT INTO animes (name) VALUES ('One Piece');
INSERT INTO animes (name) VALUES ('Attack on Titan');
INSERT INTO animes (name) VALUES ('Hunter x Hunter');
INSERT INTO animes (name) VALUES ('Jujutsu Kaisen');
INSERT INTO animes (name) VALUES ('Death Note');
INSERT INTO animes (name) VALUES ('Fullmetal Alchemist: Brotherhood');
INSERT INTO animes (name) VALUES ('Demon Slayer');
INSERT INTO animes (name) VALUES ('Boku no Hero Academia');
INSERT INTO animes (name) VALUES ('Bleach: Thousand-Year Blood War');
INSERT INTO animes (name) VALUES ('My Hero Academia');
INSERT INTO animes (name) VALUES ('Tokyo Revengers');
INSERT INTO animes (name) VALUES ('Sword Art Online');
INSERT INTO animes (name) VALUES ('Fairy Tail');
INSERT INTO animes (name) VALUES ('Dragon Ball Z');
INSERT INTO animes (name) VALUES ('Cowboy Bebop');
INSERT INTO animes (name) VALUES ('Neon Genesis Evangelion');
INSERT INTO animes (name) VALUES ('Steins; Gate');
INSERT INTO animes (name) VALUES ('Code Geass: Lelouch of the Rebellion');
INSERT INTO animes (name) VALUES ('Gintama');


-- ----------------------------------------------
-- Resetar a tabela de usuários e inserir novos usuários
-- ----------------------------------------------
DELETE FROM users;

-- ----------------------------------------------
-- Inserção de usuário ADMIN na tabela "users"
-- ADMIN
-- ----------------------------------------------
INSERT INTO users (id, username, password, role)
VALUES (
	1,
    'admin',
    '$2a$10$O9N7n7EjxX4r6Z1Y6cZ9NOBbP9C2oJv2L0Q5A0Vx0dT5W2m8Zy1gC', -- Senha com Hash: 123
    'ADMIN'
);

-- ----------------------------------------------
-- Inserção de usuário USER na tabela "users"
-- USER
-- ----------------------------------------------
INSERT INTO users (id, username, password, role)
VALUES (
	2,
    'joao',
    '$2a$10$O9N7n7EjxX4r6Z1Y6cZ9NOBbP9C2oJv2L0Q5A0Vx0dT5W2m8Zy1gC', -- Senha com Hash: 123
    'USER'
);