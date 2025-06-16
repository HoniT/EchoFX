CREATE DATABASE echo_fx;
USE echo_fx;

CREATE TABLE users(user_id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(30) UNIQUE, email VARCHAR(40) UNIQUE, password_hash VARCHAR(100), created_at DATE);
CREATE TABLE songs(address VARCHAR(150) PRIMARY KEY, user_id INT, FOREIGN KEY (user_id) REFERENCES users(user_id), title VARCHAR(20), artist VARCHAR(20), album VARCHAR(30), duration DECIMAL(4, 2), is_favorite BOOL);
CREATE TABLE playlists(playlist_id INT PRIMARY KEY, user_id INT, FOREIGN KEY (user_id) REFERENCES users(user_id), name VARCHAR(20));
CREATE TABLE playlist_songs(playlist_id INT, FOREIGN KEY (playlist_id) REFERENCES playlists(playlist_id), address VARCHAR(150), FOREIGN KEY (address) REFERENCES songs(address));