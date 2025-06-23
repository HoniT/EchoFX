package ge.mziuri.echofx.database.models;

public class PlaylistSong {
    private final int playlistId;
    private final int songId;

    public PlaylistSong(int playlistId, int songId) {
        this.playlistId = playlistId;
        this.songId = songId;
    }

    @Override
    public String toString() {
        return "PlaylistSong{" +
                "playlistId=" + playlistId +
                ", songId=" + songId +
                '}';
    }
}
