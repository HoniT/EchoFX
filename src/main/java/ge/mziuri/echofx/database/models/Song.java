package ge.mziuri.echofx.database.models;

import ge.mziuri.echofx.controllers.MainController;
import ge.mziuri.echofx.controllers.PlaylistViewController;
import ge.mziuri.echofx.controllers.SongController;
import ge.mziuri.echofx.database.repository.SongRepository;
import ge.mziuri.echofx.services.AudioPlayService;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Song {
    private int song_id;
    private final String address;
    private final int userId;
    private final String title;
    private final String artist;
    private final String album;
    private final float duration;
    private boolean is_favorite;

    public Song(int song_id, String address, int userId, String title, String artist, String album, float duration, boolean is_favorite) {
        this.song_id = song_id;
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
            controller.setPlayAction(_ -> {
                AudioPlayService.playMusic(this.address, this);
                // Setting this title as the current playing song
                MainController.controller.setCurrentSongText(this.title);
            });
            controller.setFavoriteAction(this, () -> SongRepository.addFavoriteSong(this), () -> SongRepository.removeFavoriteSong(this));
            controller.playlistButton.setOnAction(event -> {
                if(MainController.controller.onPlaylists) {
                    SongRepository.removeSongFromPlaylist(PlaylistViewController.currentPlaylistId, this);
                    MainController.controller.listSongs(SongRepository.getPlaylistSongs(PlaylistViewController.currentPlaylistId));
                }
                else
                    MainController.controller.openPlaylistPanel(event, this);
            });

            return banner;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new AnchorPane(); // Fallback
        }
    }

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
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
