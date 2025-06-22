package ge.mziuri.echofx.controllers;

import ge.mziuri.echofx.database.models.Playlist;
import ge.mziuri.echofx.database.repository.SongRepository;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class PlaylistsController {
    @FXML
    private VBox playlistDisplayPane;
    @FXML
    private Text songNameText;
    @FXML
    private TextField newPlaylistNameField;

    private void displayPlaylists() {
        playlistDisplayPane.getChildren().clear();
        List<Playlist> playlists = SongRepository.getPlaylists();
        for(Playlist playlist : playlists) {
            playlistDisplayPane.getChildren().add(playlist.createBanner());
        }
    }

    @FXML
    private void initialize() {
        displayPlaylists();
    }

    public void setSongNameText(String songName) {
        songNameText.setText(songName);
    }

    public void addNewPlaylist() {
        if(newPlaylistNameField.getText().isEmpty()) return;

        SongRepository.addPlaylist(newPlaylistNameField.getText());
        displayPlaylists();
    }
}
