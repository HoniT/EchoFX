CREATE DATABASE music_player;
USE music_player;

CREATE TABLE users(user_id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(30) UNIQUE, email VARCHAR(40) UNIQUE, password_hash VARCHAR(100), created_at DATE);
CREATE TABLE songs(song_id INT PRIMARY KEY, title VARCHAR(20), artist VARCHAR(20), album VARCHAR(30), duration DECIMAL(2, 2), api_url VARCHAR(100));
CREATE TABLE favorite_songs(user_id INT, FOREIGN KEY (user_id) REFERENCES users(user_id), song_id INT, FOREIGN KEY (song_id) REFERENCES songs(song_id), marked_at DATE);
CREATE TABLE playlists(playlist_id INT PRIMARY KEY, user_id INT, FOREIGN KEY (user_id) REFERENCES users(user_id), name VARCHAR(20), created_at DATE);
CREATE TABLE playlist_songs(playlist_id INT, FOREIGN KEY (playlist_id) REFERENCES playlists(playlist_id), song_id INT, FOREIGN KEY (song_id) REFERENCES songs(song_id));