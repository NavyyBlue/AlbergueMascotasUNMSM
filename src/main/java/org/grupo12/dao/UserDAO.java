package org.grupo12.dao;

import org.grupo12.models.User;
import org.grupo12.util.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public User getUserByUsernameAndPassword(String username, String password) {
        Connection connection = ConnectionDB.getConnection();
        String sql = "SELECT * FROM User WHERE UserName = ? AND Password = ?";
        User user = null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
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
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
