INSERT INTO category(name)
VALUES ('Eurogames');
INSERT INTO category(name)
VALUES ('Ameritrash');
INSERT INTO category(name)
VALUES ('Familiar');

INSERT INTO author(name, nationality)
VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality)
VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality)
VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality)
VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality)
VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality)
VALUES ('Phil Walker-Harding', 'US');

INSERT INTO game(title, age, category_id, author_id)
VALUES ('On Mars', '14', 1, 2);
INSERT INTO game(title, age, category_id, author_id)
VALUES ('Aventureros al tren', '8', 3, 1);
INSERT INTO game(title, age, category_id, author_id)
VALUES ('1920: Wall Street', '12', 1, 4);
INSERT INTO game(title, age, category_id, author_id)
VALUES ('Barrage', '14', 1, 3);
INSERT INTO game(title, age, category_id, author_id)
VALUES ('Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO game(title, age, category_id, author_id)
VALUES ('Azul', '8', 3, 5);

INSERT INTO client(name)
VALUES ('Juan García Pérez');
INSERT INTO client(name)
VALUES ('María López Sánchez');
INSERT INTO client(name)
VALUES ('Carlos Rodríguez Martín');
INSERT INTO client(name)
VALUES ('Ana Fernández Gómez');
INSERT INTO client(name)
VALUES ('David Martínez Ruiz');
INSERT INTO client(name)
VALUES ('Laura Jiménez Moreno');
INSERT INTO client(name)
VALUES ('Javier Hernández Torres');
INSERT INTO client(name)
VALUES ('Carmen Díaz Navarro');
INSERT INTO client(name)
VALUES ('Sergio Romero Castillo');
INSERT INTO client(name)
VALUES ('Elena Castro Vega');

INSERT INTO reservation(game_id, client_id, start_date, end_date)
VALUES (1, 1, '2026-09-01', '2026-09-10');
INSERT INTO reservation(game_id, client_id, start_date, end_date)
VALUES (2, 2, '2026-09-03', '2026-09-12');
INSERT INTO reservation(game_id, client_id, start_date, end_date)
VALUES (3, 3, '2026-09-08', '2026-09-18');
INSERT INTO reservation(game_id, client_id, start_date, end_date)
VALUES (4, 4, '2026-09-10', '2026-09-23');
INSERT INTO reservation(game_id, client_id, start_date, end_date)
VALUES (5, 5, '2026-09-15', '2026-09-25');
INSERT INTO reservation(game_id, client_id, start_date, end_date)
VALUES (6, 6, '2026-09-20', '2026-10-03');
INSERT INTO reservation(game_id, client_id, start_date, end_date)
VALUES (1, 7, '2026-09-15', '2026-09-28');