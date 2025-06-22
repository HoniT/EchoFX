package ge.mziuri.echofx;

import ge.mziuri.echofx.database.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

// Saves current user id, username... (doesn't save password hash)
public class Session {
    private static User user = new User();
    private static int skipsThisHour = 0;

    // Decoding result set gotten from SQL directly in this function so we don't have to do extra steps elsewhere
    public static void saveUser(ResultSet rs) {
        try {
            user = new User(rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                null,
                rs.getDate(5),
                rs.getBoolean(6));
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteUser() {
        user = new User();
    }

    public static User getUser() {
        return user;
    }

    public static int getSkipsThisHour() {
        return skipsThisHour;
    }

    public static void setSkipsThisHour(int skipsThisHour) {
        Session.skipsThisHour = skipsThisHour;
    }
}
