package ge.mziuri.echofx.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // URL to connect to our DB
    private static final String URL = "jdbc:mysql://localhost:3306/music_player";

    // Makes connection to MySQL database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, "root", "root");
    }
}
