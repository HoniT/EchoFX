package ge.mziuri.echofx.database.models;

import ge.mziuri.echofx.controllers.MainController;
import ge.mziuri.echofx.controllers.PlaylistController;
import ge.mziuri.echofx.controllers.PlaylistViewController;
import ge.mziuri.echofx.database.repository.SongRepository;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Playlist {
    private final int playlistId;
    private final int userId;
    private final String name;

    public Playlist(int playlistId, int userId, String name) {
        this.playlistId = playlistId;
        this.userId = userId;
        this.name = name;
    }

    public AnchorPane createBanner(boolean onPlaylistsView) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ge/mziuri/echofx/views/PlaylistView.fxml"));
            AnchorPane banner = loader.load();
            PlaylistController controller = loader.getController();
            controller.setNameText(this.name);
            if(onPlaylistsView)
                controller.addButton.setOnAction(_ -> SongRepository.addSongToPlaylist(this.playlistId, PlaylistViewController.currentSong));
            else
                controller.addButton.setOnAction(_ -> MainController.controller.listSongs(SongRepository.getPlaylistSongs(this.playlistId)));

            return banner;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "playlistId=" + playlistId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}
