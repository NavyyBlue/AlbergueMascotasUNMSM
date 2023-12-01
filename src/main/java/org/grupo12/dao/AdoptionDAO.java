package org.grupo12.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.grupo12.util.AdoptionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdoptionDAO {
    HikariDataSource dataSource;

    public AdoptionDAO(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }


    public List<String> requestAdoption(int userId, int petId) {
        String userPetExists = "SELECT UserPetId FROM UserPet WHERE UserId = ? AND PetId = ?";
        String insertAdoption = "INSERT INTO UserPet(UserId, PetId, Type) VALUES (?, ?, ?)";
        String updateAdoption = "UPDATE UserPet SET Type = ? WHERE UserPetId = ?";
        String getAllUsersToNotify = "SELECT UserId FROM UserPet WHERE PetId = ? AND IsFavorite = 1 AND UserId != ?";
        String insertNotification = "INSERT INTO UserNotification(UserId) VALUES (?)";
        String getAllEmailsToSent = "SELECT Email FROM User WHERE UserId = ?";

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);

            try(PreparedStatement statement = connection.prepareStatement(userPetExists)){
                statement.setInt(1, userId);
                statement.setInt(2, petId);

                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    int userPetId = resultSet.getInt("UserPetId");
                    try(PreparedStatement preparedStatement = connection.prepareStatement(updateAdoption)){
                        preparedStatement.setInt(1, AdoptionUtil.ADOPTAR);
                        preparedStatement.setInt(2, userPetId);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if(rowsAffected > 0){
                            try(PreparedStatement preparedStatement1 = connection.prepareStatement(getAllUsersToNotify)){
                                preparedStatement1.setInt(1, petId);
                                preparedStatement1.setInt(2, userId);

                                ResultSet resultSet1 = preparedStatement1.executeQuery();
                                while (resultSet1.next()){
                                    int userIdToNotify = resultSet1.getInt("UserId");
                                    try(PreparedStatement preparedStatement2 = connection.prepareStatement(insertNotification)){
                                        preparedStatement2.setInt(1, userIdToNotify);

                                        int rowsAffected2 = preparedStatement2.executeUpdate();
                                        if(rowsAffected2 > 0){
                                            try(PreparedStatement preparedStatement3 = connection.prepareStatement(getAllEmailsToSent)){
                                                preparedStatement3.setInt(1, userIdToNotify);

                                                ResultSet resultSet2 = preparedStatement3.executeQuery();
                                                if(resultSet2.next()){
                                                    String email = resultSet2.getString("Email");
                                                    connection.commit();
                                                    return List.of(email);
                                                }else {
                                                    connection.rollback();
                                                    return null;
                                                }
                                            }
                                        }else {
                                            connection.rollback();
                                            return null;
                                        }
                                    }
                                }
                            }
                        }

                    }
                }else{
                    try(PreparedStatement preparedStatement = connection.prepareStatement(insertAdoption)){
                        preparedStatement.setInt(1, userId);
                        preparedStatement.setInt(2, petId);
                        preparedStatement.setInt(3, AdoptionUtil.ADOPTAR);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if(rowsAffected > 0){
                            try(PreparedStatement preparedStatement1 = connection.prepareStatement(getAllUsersToNotify)){
                                preparedStatement1.setInt(1, petId);
                                preparedStatement1.setInt(2, userId);

                                ResultSet resultSet1 = preparedStatement1.executeQuery();
                                while (resultSet1.next()){
                                    int userIdToNotify = resultSet1.getInt("UserId");
                                    try(PreparedStatement preparedStatement2 = connection.prepareStatement(insertNotification)){
                                        preparedStatement2.setInt(1, userIdToNotify);

                                        int rowsAffected2 = preparedStatement2.executeUpdate();
                                        if(rowsAffected2 > 0){
                                            try(PreparedStatement preparedStatement3 = connection.prepareStatement(getAllEmailsToSent)){
                                                preparedStatement3.setInt(1, userIdToNotify);

                                                ResultSet resultSet2 = preparedStatement3.executeQuery();
                                                if(resultSet2.next()){
                                                    String email = resultSet2.getString("Email");
                                                    connection.commit();
                                                    return List.of(email);
                                                }else {
                                                    connection.rollback();
                                                    return null;
                                                }
                                            }
                                        }else {
                                            connection.rollback();
                                            return null;
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                connection.rollback();
                return null;
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public List<String> rejectAdoption(int userPetId) {
        String getUserAndPet = "SELECT UserId, PetId FROM UserPet WHERE UserPetId = ?";
        String updateAdoption = "UPDATE UserPet SET Type = ? WHERE UserPetId = ?";
        String getAllUsersToNotify = "SELECT UserId FROM UserPet WHERE PetId = ? AND IsFavorite = 1 AND UserId != ?";
        String insertNotification = "INSERT INTO UserNotification(UserId) VALUES (?)";
        String getAllEmailsToSent = "SELECT Email FROM User WHERE UserId = ?";

        try(Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try(PreparedStatement preparedStatement = connection.prepareStatement(getUserAndPet)) {
                preparedStatement.setInt(1, userPetId);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int userId = resultSet.getInt("UserId");
                    int petId = resultSet.getInt("PetId");
                    try (PreparedStatement preparedStatement1 = connection.prepareStatement(updateAdoption)) {
                        preparedStatement1.setInt(1, AdoptionUtil.RECHAZAR);
                        preparedStatement1.setInt(2, userPetId);

                        int rowsAffected = preparedStatement1.executeUpdate();
                        if (rowsAffected > 0) {
                            try (PreparedStatement preparedStatement2 = connection.prepareStatement(getAllUsersToNotify)) {
                                preparedStatement2.setInt(1, petId);
                                preparedStatement2.setInt(2, userId);

                                ResultSet resultSet1 = preparedStatement2.executeQuery();
                                while (resultSet1.next()) {
                                    int userIdToNotify = resultSet1.getInt("UserId");
                                    try (PreparedStatement preparedStatement3 = connection.prepareStatement(insertNotification)) {
                                        preparedStatement3.setInt(1, userIdToNotify);

                                        int rowsAffected2 = preparedStatement3.executeUpdate();
                                        if (rowsAffected2 > 0) {
                                            try (PreparedStatement preparedStatement4 = connection.prepareStatement(getAllEmailsToSent)) {
                                                preparedStatement4.setInt(1, userIdToNotify);

                                                ResultSet resultSet2 = preparedStatement4.executeQuery();
                                                if (resultSet2.next()) {
                                                    String email = resultSet2.getString("Email");
                                                    connection.commit();
                                                    return List.of(email);
                                                } else {
                                                    connection.rollback();
                                                    return null;
                                                }
                                            }
                                        } else {
                                            connection.rollback();
                                            return null;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                connection.rollback();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public String getPetName(int petId) {
        String getPetName = "SELECT Name FROM Pet WHERE PetId = ?";
        try(Connection connection = dataSource.getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(getPetName)){
                preparedStatement.setInt(1, petId);

                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    return resultSet.getString("Name");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
