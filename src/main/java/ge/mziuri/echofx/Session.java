package ge.mziuri.echofx;

import ge.mziuri.echofx.database.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

// Saves current user id, username... (doesn't save password hash)
public class Session {
    private static User user = new User();

    // Decoding result set gotten from SQL directly in this function so we don't have to do extra steps elsewhere
    public static void saveUser(ResultSet rs) throws SQLException {
        user.setUserId(rs.getInt(1));
        user.setUsername(rs.getString(2));
        user.setEmail(rs.getString(3));
        user.setPasswordHash(null);
        user.setCreatedAt(rs.getDate(5));
    }

    public static void deleteUser() { user = null; }

    public User getUserId() {
        return user;
    }
}
