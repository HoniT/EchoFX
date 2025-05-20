package ge.mziuri.echofx.database.models;

import java.util.Date;

public class Playlist {
    private int playlistId;
    private int userId;
    private String name;
    private Date createAt;

    public Playlist(int playlistId, int userId, String name, Date createAt) {
        this.playlistId = playlistId;
        this.userId = userId;
        this.name = name;
        this.createAt = createAt;
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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "playlistId=" + playlistId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
