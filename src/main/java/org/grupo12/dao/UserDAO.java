package org.grupo12.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.grupo12.models.User;
import org.grupo12.util.PasswordEncryptionUtil;

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
        String sql = "SELECT * FROM User WHERE UserName = ? AND Password = ? AND Active = 1";
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
                user.setUserRole(result.getInt("UserRole"));
                user.setActive(result.getBoolean("Active"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public String getHashedPasswordByUsername(String username) {
        String sql = "SELECT Password FROM User WHERE UserName = ? AND Active = 1";
        String hashedPassword = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                hashedPassword = result.getString("Password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hashedPassword;
    }

    public List<User> getUsers(int userId, int active, int offset, int limit){
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
                    "FROM User ");

        List<Object> parameters = new ArrayList<>();
        if(active != 2){
            sqlBuilder.append("WHERE Active = ? ");
            parameters.add(active);
        }else {
            sqlBuilder.append("WHERE Active = 1 OR Active = 0 ");
        }

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
                user.setUserRole(result.getInt("UserRole"));
                user.setActive(result.getBoolean("Active"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public int getTotalCountUsers(int active){
        int total = 0;
        StringBuilder sqlBuilder = new StringBuilder( "SELECT COUNT(*) AS total FROM User ");

        List<Object> parameters = new ArrayList<>();
        if(active != 2) {
            sqlBuilder.append("WHERE Active = ? ");
            parameters.add(active);
        }else {
            sqlBuilder.append("WHERE Active = 1 OR Active = 0 ");
        }

        String countSql = sqlBuilder.toString();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement countStatement = connection.prepareStatement(countSql)) {

            int paramIndex = 1;
            for (Object parameter : parameters) {
                countStatement.setObject(paramIndex++, parameter);
            }
            ResultSet countResult = countStatement.executeQuery();

            if (countResult.next()) {
                total = countResult.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    public boolean updateUser(User user){
        String sql = "UPDATE User SET FirstName = ?, LastName = ?, Email = ?, UserName = ?, PhoneNumber = ?, UserRole = ? WHERE UserId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUserName());
            statement.setString(5, user.getPhoneNumber());
            statement.setInt(6, user.getUserRole());
            statement.setInt(7, user.getUserId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int userId){
        String sql = "UPDATE User SET Active = 0 WHERE UserId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean restoreUser(int userId){
        String sql = "UPDATE User SET Active = 1 WHERE UserId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createUser(User user){
        String sql = "INSERT INTO User (FirstName, LastName, Email, UserName, Password, PhoneNumber) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUserName());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getPhoneNumber());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserPassword(int userId, String newPassword){
        String sql = "UPDATE User SET Password = ? WHERE UserId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newPassword);
            statement.setInt(2, userId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyUserExistenceByEmail(String email){
        String sql = "SELECT COUNT(*) FROM User WHERE Email = ? AND Active = 1";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return result.getInt(1) > 0;
            }else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
