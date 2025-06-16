package ge.mziuri.echofx.services;

import ge.mziuri.echofx.Session;
import ge.mziuri.echofx.database.models.Song;

import java.io.File;
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

    // Translates a result set into a song object
    private static Song getSong(String file) {
        return new Song(file, Session.getUser().getUserId(), getTitle(file), "DefaultArtist", "DefaultAlbum", 1.0f, false);
    }

    public static List<Song> songs = new ArrayList<>();
    // Gets every song assigned to user
    public static List<Song> getUserSongs() {
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
