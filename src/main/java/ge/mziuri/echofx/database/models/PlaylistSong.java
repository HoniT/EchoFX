package ge.mziuri.echofx.database.models;

public class PlaylistSong {
    private int playlistId;
    private int songId;

    public PlaylistSong(int playlistId, int songId) {
        this.playlistId = playlistId;
        this.songId = songId;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
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
