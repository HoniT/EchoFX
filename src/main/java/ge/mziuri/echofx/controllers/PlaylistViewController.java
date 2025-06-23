package ge.mziuri.echofx.controllers;

import ge.mziuri.echofx.database.models.Playlist;
import ge.mziuri.echofx.database.models.Song;
import ge.mziuri.echofx.database.repository.SongRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class PlaylistViewController {
    @FXML
    private VBox playlistDisplayPane;
    @FXML
    private Text songNameText;
    @FXML
    private TextField newPlaylistNameField;

    public static void displayPlaylists(VBox display, boolean onPlaylistsView) {
        display.getChildren().clear();
        List<Playlist> playlists = SongRepository.getPlaylists();
        for(Playlist playlist : playlists) {
            display.getChildren().add(playlist.createBanner(onPlaylistsView));
        }
    }

    @FXML
    private void initialize() {
        PlaylistViewController.displayPlaylists(playlistDisplayPane, true);
    }

    public static Song currentSong;
    public void setSongNameText(Song song) {
        currentSong = song;
        songNameText.setText(song.getTitle());
    }

    public void addNewPlaylist() {
        if(newPlaylistNameField.getText().isEmpty()) return;

        SongRepository.addPlaylist(newPlaylistNameField.getText());
        PlaylistViewController.displayPlaylists(playlistDisplayPane, true);
    }

    @FXML
    private void backToHome(ActionEvent event) {
        MainController.controller.loadMainView(event);
    }
}
