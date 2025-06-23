package ge.mziuri.echofx.database.models;

import java.util.Date;

public class User {
    private int userId;
    private String username;
    private String email;
    private String passwordHash;
    private Date createdAt;
    private boolean premium;

    public User() {}

    public User(int userId, String username, String email, String passwordHash, Date createdAt, boolean premium) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.premium = premium;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", createdAt=" + createdAt +
                ", premium=" + premium +
                '}';
    }
}
