package ge.mziuri.echofx.database.repository;

import ge.mziuri.echofx.Session;
import ge.mziuri.echofx.database.Database;
import ge.mziuri.echofx.database.models.Song;
import ge.mziuri.echofx.services.SongService;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    // Gets user info from database
    public static boolean logIntoUser(String username, String passwordHash) {
        try {
            // Executing query
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password_hash = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, passwordHash);
            ResultSet resultSet = preparedStatement.executeQuery();
            // If nothing was returned
            if (!resultSet.next()) {
                System.out.println("Username or password incorrect!");
                return false;
            }

            // Saving user info (excluding password hash for security)
            Session.saveUser(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    // Adds a user to database
    public static boolean addUser(String username, String email, String passwordHash) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ? OR email = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            // If a user with this username already exists
            if (resultSet.next()) {
                System.out.println("User with the same name or email already exists!");
                return false;
            }

            // Adding user
            preparedStatement = connection.prepareStatement("INSERT INTO users (username, email, password_hash, created_at) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, passwordHash);
            // Getting and setting current date
            Date date = Date.valueOf(LocalDate.now());
            preparedStatement.setDate(4, date);
            preparedStatement.execute();

            // Logging into new user
            logIntoUser(username, passwordHash);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    // Translates a result set into a song object
    private static Song getSong(String file) {
        File audioFile = new File(file);
        String fileNameWithExt = audioFile.getName();

        String fileName = fileNameWithExt.contains(".")
                ? fileNameWithExt.substring(0, fileNameWithExt.lastIndexOf('.'))
                : fileNameWithExt; // If there's no extension

        return new Song(Session.getUser().getUserId(), fileName, "DefaultArtist", "DefaultAlbum", 1.0f, file);
    }

    // Gets every song assigned to user
    public static List<Song> getUserSongs() {
        List<Song> songs = new ArrayList<>();
        // Getting downloaded songs and turning them to Song objects
        List<String> files = SongService.getDownloadedSongs();
        for(String file : files) {
            songs.add(getSong(file));
        }

        return songs;
    }

    // Adds song to DB
    public static void addSong(Song song) {
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement("INSERT INTO songs (user_id, title, artist, album, duration, address) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, song.getUserId());
            preparedStatement.setString(2, song.getTitle());
            preparedStatement.setString(3, song.getArtist());
            preparedStatement.setString(4, song.getAlbum());
            preparedStatement.setFloat(5, song.getDuration());
            preparedStatement.setString(6, song.getAddress());
            preparedStatement.execute();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
