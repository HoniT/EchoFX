package ge.mziuri.echofx.database.repository;

import ge.mziuri.echofx.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;

public class UserRepository {
    public static boolean logIntoUser(String username, String passwordHash) throws SQLException {
        // Executing query
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password_hash = ?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, passwordHash);
        ResultSet resultSet = preparedStatement.executeQuery();
        // If nothing was returned
        if(!resultSet.next())
        {
            System.out.println("Username or password incorrect!");
            return false;
        }

        // For now printing info
        System.out.println(resultSet.getInt(1));
        System.out.println(resultSet.getString(2));
        System.out.println(resultSet.getString(3));
        System.out.println(resultSet.getString(4));
        System.out.println(resultSet.getDate(5));
        return true;
    }

    public static boolean addUser(String username, String email, String passwordHash) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ? OR email = ?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        // If a user with this username already exists
        if(resultSet.next()) {
            System.out.println("User with the same name or email already exists!");
            return false;
        }

        // Adding user
        preparedStatement = connection.prepareStatement("INSERT INTO users (username, email, password_hash, created_at) VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, email);
        preparedStatement.setString(3, passwordHash);
        // Getting and setting current date
        Date date = Date.valueOf(LocalDate.now());
        preparedStatement.setDate(4, date);
        preparedStatement.execute();
        System.out.println("Added user");
        return true;
    }
}
