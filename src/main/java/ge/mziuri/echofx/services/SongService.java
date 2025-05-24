package ge.mziuri.echofx.services;

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
