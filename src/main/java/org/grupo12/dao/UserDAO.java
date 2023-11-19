package org.grupo12.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.grupo12.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<User> getUsers(int userId, int offset, int limit){
        List<User> users = new ArrayList<>();
        StringBuilder sqlBuilder =new StringBuilder(
                "SELECT " +
                    " UserId, " +
                    " FirstName, " +
                    " LastName, " +
                    " Email, " +
                    " UserName, " +
                    " PhoneNumber, " +
                    " UserImage, " +
                    " UserRole, " +
                    " Active " +
                    "FROM User " +
                "WHERE Active = 1 ");

        List<Object> parameters = new ArrayList<>();

        if(userId != 0){
            sqlBuilder.append("AND UserId = ? ");
            parameters.add(userId);
        }
        // Add limit and offset
        sqlBuilder.append("LIMIT ? OFFSET ?");
        parameters.add(limit);
        parameters.add(offset);


        String sql = sqlBuilder.toString();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            int paramIndex = 1;
            for (Object parameter : parameters) {
                statement.setObject(paramIndex++, parameter);
            }

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                User user = new User();
                user.setUserId(result.getInt("UserId"));
                user.setFirstName(result.getString("FirstName"));
                user.setLastName(result.getString("LastName"));
                user.setEmail(result.getString("Email"));
                user.setUserName(result.getString("UserName"));
                user.setPhoneNumber(result.getString("PhoneNumber"));
                user.setUserImage(result.getString("UserImage"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public int getTotalCountUsers(){
        int total = 0;
        String countSql = "SELECT COUNT(*) AS total FROM User WHERE Active = 1";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement countStatement = connection.prepareStatement(countSql)) {

            ResultSet countResult = countStatement.executeQuery();

            if (countResult.next()) {
                total = countResult.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    public void updateUser(User user){
        String sql = "UPDATE User SET FirstName = ?, LastName = ?, Email = ?, UserName = ?, PhoneNumber = ? WHERE UserId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUserName());
            statement.setString(5, user.getPhoneNumber());
            statement.setInt(7, user.getUserId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
