package ge.mziuri.echofx.database.repository;

import ge.mziuri.echofx.Session;
import ge.mziuri.echofx.database.Database;
import ge.mziuri.echofx.database.models.Playlist;
import ge.mziuri.echofx.database.models.Song;
import ge.mziuri.echofx.services.SongService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

            song.setSong_id(retrieveSongId(song.getAddress()));
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void removeSong(Song song) {
        try {
            // Deleting from playlist_songs if we can
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement("DELETE FROM playlist_songs WHERE song_id = ?");
            preparedStatement.setInt(1, song.getSong_id());
            preparedStatement.execute();

            preparedStatement = Database.getConnection().prepareStatement("DELETE FROM songs WHERE address = ? AND user_id = ?");
            preparedStatement.setString(1, song.getAddress());
            preparedStatement.setInt(2, Session.getUser().getUserId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Retrieves the song id for a users song
    public static int retrieveSongId(String address) {
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement("SELECT song_id FROM songs WHERE user_id = ? AND address = ?");
            preparedStatement.setInt(1, Session.getUser().getUserId());
            preparedStatement.setString(2, address);
            ResultSet rs = preparedStatement.executeQuery();
            if(!rs.next()) return 0;

            return rs.getInt(1);
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

    public static List<Song> favoriteSongs = new ArrayList<>();
    public static List<Song> getFavoriteSongs() {
        favoriteSongs.clear();
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement("SELECT * FROM songs WHERE is_favorite = 1 AND user_id = ?");
            preparedStatement.setInt(1, Session.getUser().getUserId());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) favoriteSongs.add(SongService.rsToSong(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return favoriteSongs;
    }

    // <editor-fold desc="Playlist Management">

    // Retrieves a playlists of a given name
    public static Playlist retrievePlaylist(String name) {
        try {
            // Retrieves info from DB
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement("Select * FROM playlists WHERE user_id = ? AND name = ?");
            preparedStatement.setInt(1, Session.getUser().getUserId());
            preparedStatement.setString(2, name);
            ResultSet rs = preparedStatement.executeQuery();

            if(!rs.next()) return null;
            return new Playlist(rs.getInt(1), rs.getInt(2), rs.getString(3));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Playlist> getPlaylists() {
        try {
            List<Playlist> playlists = new ArrayList<>();
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement("SELECT * FROM playlists WHERE user_id = ?");
            preparedStatement.setInt(1, Session.getUser().getUserId());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                playlists.add(new Playlist(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3)));
            }
            return playlists;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<Song> getPlaylistSongs(int playlist_id) {
        try {
            List<Song> songs = new ArrayList<>();

            String sql = """
                SELECT s.song_id, s.address, s.user_id, s.title, s.artist, s.album, s.duration, s.is_favorite
                FROM songs s
                JOIN playlist_songs ps ON s.song_id = ps.song_id
                WHERE ps.playlist_id = ?
            """;
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, playlist_id);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                songs.add(SongService.rsToSong(rs));
            }

            return songs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addPlaylist(String name) {
        // If it already exists we'll skip
        if(retrievePlaylist(name) != null) {
            System.out.println("Playlist already exists!");
            return;
        }

        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement("INSERT INTO playlists (user_id, name) VALUES (?, ?)");
            preparedStatement.setInt(1, Session.getUser().getUserId());
            preparedStatement.setString(2, name);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deletePlaylist(int playlist_id) {
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement("DELETE FROM playlist_songs WHERE playlist_id = ?");
            preparedStatement.setInt(1, playlist_id);
            preparedStatement.execute();

            preparedStatement = Database.getConnection().prepareStatement("DELETE FROM playlists WHERE playlist_id = ?");
            preparedStatement.setInt(1, playlist_id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean getPlaylistSong(int playlist_id, Song song) {
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement("SELECT * FROM playlist_songs WHERE playlist_id = ? and song_id = ?");
            preparedStatement.setInt(1, playlist_id);
            preparedStatement.setInt(2, song.getSong_id());
            ResultSet rs = preparedStatement.executeQuery();

            if(!rs.next()) return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static void addSongToPlaylist(int playlist_id, Song song) {
        // If it's already in playlist returning
        if(getPlaylistSong(playlist_id, song)) {
            System.out.println("Song already in playlist!");
            return;
        }

        // Adding song to DB if not already added
        if(retrieveSongId(song.getAddress()) == 0) addSong(song);

        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement("INSERT INTO playlist_songs VALUES (?, ?)");
            preparedStatement.setInt(1, playlist_id);
            preparedStatement.setInt(2, song.getSong_id());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeSongFromPlaylist(int playlist_id, Song song) {
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement("DELETE FROM playlist_songs WHERE playlist_id = ? AND song_id = ?");
            preparedStatement.setInt(1, playlist_id);
            preparedStatement.setInt(2,song.getSong_id());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // </editor-fold>
}
