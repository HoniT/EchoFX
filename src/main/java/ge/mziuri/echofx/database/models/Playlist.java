package ge.mziuri.echofx.database.models;

import ge.mziuri.echofx.controllers.PlaylistController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Playlist {
    private int playlistId;
    private int userId;
    private String name;

    public Playlist(int playlistId, int userId, String name) {
        this.playlistId = playlistId;
        this.userId = userId;
        this.name = name;
    }

    public AnchorPane createBanner() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ge/mziuri/echofx/views/PlaylistView.fxml"));
            AnchorPane banner = loader.load();
            PlaylistController controller = loader.getController();
            controller.setNameText(this.name);


            return banner;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
