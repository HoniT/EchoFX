package ge.mziuri.echofx.database.models;

import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class Song {
    private int songId;
    private String title;
    private String artist;
    private String album;
    private float duration;
    private String apiUrl;

    public Song(int songId, String title, String artist, String album, float duration, String apiUrl) {
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.apiUrl = apiUrl;
    }

    // Creates a banner for this song
    public AnchorPane createBanner() {
        AnchorPane anchorPane = new AnchorPane();
        Text titleText = new Text(this.title);
        anchorPane.getChildren().add(titleText);
        return anchorPane;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
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

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songId=" + songId +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", duration=" + duration +
                ", apiUrl='" + apiUrl + '\'' +
                '}';
    }
}
