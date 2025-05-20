package ge.mziuri.echofx.database.models;

import java.util.Date;

public class FavoriteSong {
    private int userId;
    private int songId;
    private Date markedAt;

    public FavoriteSong(int userId, int songId, Date markedAt) {
        this.userId = userId;
        this.songId = songId;
        this.markedAt = markedAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public Date getMarkedAt() {
        return markedAt;
    }

    public void setMarkedAt(Date markedAt) {
        this.markedAt = markedAt;
    }

    @Override
    public String toString() {
        return "FavoriteSong{" +
                "userId=" + userId +
                ", songId=" + songId +
                ", markedAt=" + markedAt +
                '}';
    }
}
