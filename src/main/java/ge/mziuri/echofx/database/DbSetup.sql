
-- Creating database
CREATE DATABASE echo_fx;
USE echo_fx;

-- Creating tables
CREATE TABLE users(user_id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(30) UNIQUE, email VARCHAR(40) UNIQUE, password_hash VARCHAR(100), created_at DATE, premium BOOL);
CREATE TABLE songs(song_id INT AUTO_INCREMENT PRIMARY KEY, address VARCHAR(150), user_id INT, FOREIGN KEY (user_id) REFERENCES users(user_id), title VARCHAR(50), artist VARCHAR(20), album VARCHAR(30), duration DECIMAL(4, 2), is_favorite BOOL);
CREATE TABLE playlists(playlist_id INT AUTO_INCREMENT PRIMARY KEY, user_id INT, FOREIGN KEY (user_id) REFERENCES users(user_id), name VARCHAR(20));
CREATE TABLE playlist_songs(playlist_id INT, FOREIGN KEY (playlist_id) REFERENCES playlists(playlist_id), song_id INT, FOREIGN KEY (song_id) REFERENCES songs(song_id));

-- Adding admin and test user with username and password as "root" and "user" respectively
INSERT INTO users VALUE
    (1, 'root', 'root@email.com', '4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2', '2025-06-18', 1),
    (2, 'user', 'user@email.com', '04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb', '2025-06-18', 0);
    