package ge.mziuri.echofx.services;

import ge.mziuri.echofx.Session;
import ge.mziuri.echofx.database.models.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongService {
    // Audio file types that JavaFX MediaPlayer supports
    private static final String[] audioFileTypes = {
            ".mp3",
            ".aiff",
            ".aif",
            ".aifc",
            ".wav",
            ".m4a"
    };

    // Returns file name from a full address
    public static String getTitle(String file) {
        File audioFile = new File(file);
        String fileNameWithExt = audioFile.getName();

        return fileNameWithExt.contains(".")
                ? fileNameWithExt.substring(0, fileNameWithExt.lastIndexOf('.'))
                : fileNameWithExt; // If there's no extension
    }

    // Returns a song from a file address
    private static Song getSong(String file) {
        Media media = new Media(file);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(() -> {
            double minutes = media.getDuration().toMinutes();

            // Round to 2 decimal places
            float durationInMinutes = Math.round(minutes * 100f) / 100f;
        });

        return new Song(0, file, Session.getUser().getUserId(), getTitle(file), "DefaultArtist", "DefaultAlbum", 0.00f, false);
    }

    // Translates a result set into a song object
    public static Song rsToSong(ResultSet rs) {
        try {
            return new Song(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getFloat(7), rs.getBoolean(8));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Song> songs = new ArrayList<>();
    // Gets every song assigned to user
    public static List<Song> getUserSongs() {
        songs.clear();
        // Getting downloaded songs and turning them to Song objects
        List<String> files = SongService.getDownloadedSongs();
        for(String file : files) {
            songs.add(getSong(file));
        }

        return songs;
    }

    // Gets downloaded songs from default path
    public static List<String> getDownloadedSongs() {
        return getDownloadedSongs("src/main/resources/ge/mziuri/echofx/audio");
    }

    // Gets downloaded songs with given path
    public static List<String> getDownloadedSongs(String path) {
        List<String> audioFiles = new ArrayList<>();
        // Getting audio files from resources
        File dir = new File(path);
        File[] files = dir.listFiles();

        if (files != null) {
            FILE_LOOP: for (File file : files) {
                if(!file.isFile()) continue;
                // Checking if the gotten file is supported
                for(String fileType : audioFileTypes) {
                    if(file.getName().toLowerCase().endsWith(fileType)) {
                        // Adding audio file
                        audioFiles.add(file.toURI().toString());
                        continue FILE_LOOP;
                    }
                }
            }
        }
        return audioFiles;
    }
}
