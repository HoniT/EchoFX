package ge.mziuri.echofx.controllers;

import ge.mziuri.echofx.database.models.Song;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.List;

public class MainController {
    @FXML
    private VBox songDisplayPane;

    public void listSongs(List<Song> songs) {
        // Listing every song
        for(Song song : songs) {
            songDisplayPane.getChildren().add(song.createBanner());
        }
    }
}
