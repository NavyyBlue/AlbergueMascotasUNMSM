package org.grupo12.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.grupo12.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private HikariDataSource dataSource;

    public UserDAO(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }
    public User getUserByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM User WHERE UserName = ? AND Password = ?";
        User user = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                user = new User();
                user.setUserId(result.getInt("UserId"));
                user.setFirstName(result.getString("FirstName"));
                user.setLastName(result.getString("LastName"));
                user.setEmail(result.getString("Email"));
                user.setUserImage(result.getString("UserImage"));
                user.setUserName(result.getString("UserName"));
                user.setPhoneNumber(result.getString("PhoneNumber"));
                user.setActive(result.getBoolean("Active"));
                user.setUserRole(result.getInt("UserRole"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
