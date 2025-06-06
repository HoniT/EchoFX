package ge.mziuri.echofx.database.models;

import ge.mziuri.echofx.controllers.MainController;
import ge.mziuri.echofx.controllers.SongController;
import ge.mziuri.echofx.services.AudioPlayService;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Song {
    private int songId;
    private int userId;
    private String title;
    private String artist;
    private String album;
    private float duration;
    private String address;

    public Song(int userId, String title, String artist, String album, float duration, String address) {
        this.userId = userId;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.address = address;
    }

    // Creates a banner for this song
    public AnchorPane createBanner() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ge/mziuri/echofx/views/SongView.fxml"));
            AnchorPane banner = loader.load();

            // Printing song info on song banner
            SongController controller = loader.getController();
            controller.setTitle(this.title);
            controller.setArtist(this.artist);
            controller.setPlayAction(event -> {
                AudioPlayService.playMusic(this.address);
                // Setting this title as the current playing song
                MainController.controller.setCurrentSongText(this.title);
            });

            return banner;
        } catch (IOException e) {
            e.printStackTrace();
            return new AnchorPane(); // fallback
        }
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songId=" + songId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", duration=" + duration +
                ", address='" + address + '\'' +
                '}';
    }
}
