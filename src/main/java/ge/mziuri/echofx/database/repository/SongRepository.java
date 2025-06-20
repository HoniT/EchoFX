package ge.mziuri.echofx.database.repository;

import ge.mziuri.echofx.Session;
import ge.mziuri.echofx.database.Database;
import ge.mziuri.echofx.database.models.Song;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SongRepository {
    // Adds song to DB
    private static void addSong(Song song) {
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement("INSERT INTO songs (address, user_id, title, artist, album, duration, is_favorite) VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, song.getAddress());
            preparedStatement.setInt(2, song.getUserId());
            preparedStatement.setString(3, song.getTitle());
            preparedStatement.setString(4, song.getArtist());
            preparedStatement.setString(5, song.getAlbum());
            preparedStatement.setFloat(6, song.getDuration());
            preparedStatement.setBoolean(7, song.isIs_favorite());
            preparedStatement.execute();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void removeSong(Song song) {
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement("DELETE FROM songs WHERE address = ? AND user_id = ?");
            preparedStatement.setString(1, song.getAddress());
            preparedStatement.setInt(2, Session.getUser().getUserId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addFavoriteSong(Song song) {
        // Marking as favorite and adding to DB
        song.setIs_favorite(true);
        addSong(song);
    }

    public static void removeFavoriteSong(Song song) {
        // Marking as non-favorite and removing from DB
        removeSong(song);
        song.setIs_favorite(false);
    }

    public static boolean checkFavoriteSong(Song song) {
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement("SELECT * FROM songs WHERE is_favorite = 1 AND address = ? AND user_id = ?");
            preparedStatement.setString(1, song.getAddress());
            preparedStatement.setInt(2, Session.getUser().getUserId());
            ResultSet rs = preparedStatement.executeQuery();
            if(!rs.next()) return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
