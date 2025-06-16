package ge.mziuri.echofx.database.models;

import ge.mziuri.echofx.controllers.MainController;
import ge.mziuri.echofx.controllers.SongController;
import ge.mziuri.echofx.database.repository.SongRepository;
import ge.mziuri.echofx.services.AudioPlayService;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Song {
    private String address;
    private int userId;
    private String title;
    private String artist;
    private String album;
    private float duration;
    private boolean is_favorite;

    public Song(String address, int userId, String title, String artist, String album, float duration, boolean is_favorite) {
        this.address = address;
        this.userId = userId;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.is_favorite = is_favorite;
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
                AudioPlayService.playMusic(this.address, this);
                // Setting this title as the current playing song
                MainController.controller.setCurrentSongText(this.title);
            });
            controller.setFavoriteAction(this, () -> SongRepository.addFavoriteSong(this), () -> SongRepository.removeFavoriteSong(this));

            return banner;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new AnchorPane(); // Fallback
        }
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public float getDuration() {
        return duration;
    }

    public String getAddress() {
        return address;
    }

    public boolean isIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(boolean is_favorite) {
        this.is_favorite = is_favorite;
    }

    @Override
    public String toString() {
        return "Song{" +
                "address=" + address +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", duration=" + duration +
                ", is_favorite='" + is_favorite + '\'' +
                '}';
    }
}
