package org.grupo12.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.grupo12.models.Adoption;
import org.grupo12.util.AdoptionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdoptionDAO {
    HikariDataSource dataSource;

    public AdoptionDAO(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean requestAdoption(int userId, int petId){
        String userPetExists = "SELECT UserPetId FROM UserPet WHERE UserId = ? AND PetId = ?";
        String insertAdoption = "INSERT INTO UserPet(UserId, PetId, Type) VALUES (?, ?, ?)";
        String updateAdoption = "UPDATE UserPet SET Type = ? WHERE UserPetId = ?";
        String changeAdoptionStatus = "UPDATE Pet SET AdoptionStatusId = ? WHERE PetId = ?";

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);

            try(PreparedStatement preparedStatement = connection.prepareStatement(userPetExists)){
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, petId);

                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    int userPetId = resultSet.getInt("UserPetId");
                    try(PreparedStatement preparedStatement1 = connection.prepareStatement(updateAdoption)){
                        preparedStatement1.setInt(1, AdoptionUtil.ADOPTAR);
                        preparedStatement1.setInt(2, userPetId);

                        int rowsAffected = preparedStatement1.executeUpdate();
                        if(rowsAffected > 0){
                            try(PreparedStatement preparedStatement2 = connection.prepareStatement(changeAdoptionStatus)){
                                preparedStatement2.setInt(1, AdoptionUtil.EN_PROCESO);
                                preparedStatement2.setInt(2, petId);

                                int rowsAffected1 = preparedStatement2.executeUpdate();
                                if(rowsAffected1 > 0){
                                    connection.commit();
                                    return true;
                                }else{
                                    connection.rollback();
                                    return false;
                                }
                            }
                        }else{
                            connection.rollback();
                            return false;
                        }
                    }
                }else{
                    try(PreparedStatement preparedStatement1 = connection.prepareStatement(insertAdoption)){
                        preparedStatement1.setInt(1, userId);
                        preparedStatement1.setInt(2, petId);
                        preparedStatement1.setInt(3, AdoptionUtil.ADOPTAR);

                        int rowsAffected = preparedStatement1.executeUpdate();
                        if(rowsAffected > 0){
                            try(PreparedStatement preparedStatement2 = connection.prepareStatement(changeAdoptionStatus)){
                                preparedStatement2.setInt(1, AdoptionUtil.EN_PROCESO);
                                preparedStatement2.setInt(2, petId);

                                int rowsAffected1 = preparedStatement2.executeUpdate();
                                if(rowsAffected1 > 0){
                                    connection.commit();
                                    return true;
                                }else{
                                    connection.rollback();
                                    return false;
                                }
                            }
                        }else{
                            connection.rollback();
                            return false;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String requestSponsor(int userId, int petId, double amount, int methodPayment){
        String userPetExists = "SELECT UserPetId FROM UserPet WHERE UserId = ? AND PetId = ?";
        String insertSponsor = "INSERT INTO UserPet(UserId, PetId, Type) VALUES (?, ?, ?)";
        String updateSponsor = "UPDATE UserPet SET Type = ? WHERE UserPetId = ?";
        String getEmailToSent = "SELECT Email FROM User WHERE UserId = ?";
        String insertSponsorPayment = "INSERT INTO SponsorPayment" +
                "(UserPetId, Amount, MethodPaymentId, Active) VALUES(?, ?, ?, 1);";

        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);

            try(PreparedStatement statement = connection.prepareStatement(userPetExists)){
                statement.setInt(1, userId);
                statement.setInt(2, petId);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    int userPetId = resultSet.getInt("UserPetId");
                    try(PreparedStatement preparedStatement = connection.prepareStatement(updateSponsor)){
                        preparedStatement.setInt(1, AdoptionUtil.APADRINAR);
                        preparedStatement.setInt(2, userPetId);
                        int rowsAffected = preparedStatement.executeUpdate();
                        if(rowsAffected > 0){
                            try(PreparedStatement preparedStatement1 = connection.prepareStatement(insertSponsorPayment)){
                                preparedStatement1.setInt(1, userPetId);
                                preparedStatement1.setDouble(2, amount);
                                preparedStatement1.setInt(3, methodPayment);
                                int rowsAffected1 = preparedStatement1.executeUpdate();
                                if (rowsAffected1 > 0){
                                    try(PreparedStatement preparedStatement3 = connection.prepareStatement(getEmailToSent)){
                                        preparedStatement3.setInt(1, userId);
                                        ResultSet resultSet2 = preparedStatement3.executeQuery();
                                        if(resultSet2.next()){
                                            String email = resultSet2.getString("Email");
                                            connection.commit();
                                            return email;
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
                        }else {
                            connection.rollback();
                            return null;
                        }
                    }
                }else{
                    try(PreparedStatement preparedStatement = connection.prepareStatement(insertSponsor)){
                        preparedStatement.setInt(1, userId);
                        preparedStatement.setInt(2, petId);
                        preparedStatement.setInt(3, AdoptionUtil.APADRINAR);
                        int rowsAffected = preparedStatement.executeUpdate();
                        if(rowsAffected > 0){
                            try(PreparedStatement preparedStatement1 = connection.prepareStatement(userPetExists)){
                                preparedStatement1.setInt(1, userId);
                                preparedStatement1.setInt(2, petId);
                                ResultSet resultSet1 = preparedStatement1.executeQuery();
                                if(resultSet1.next()){
                                    int userPetId = resultSet1.getInt("UserPetId");
                                    try(PreparedStatement preparedStatement2 = connection.prepareStatement(insertSponsorPayment)){
                                        preparedStatement2.setInt(1, userPetId);
                                        preparedStatement2.setDouble(2, amount);
                                        preparedStatement2.setInt(3, methodPayment);
                                        ResultSet resultSet2 = preparedStatement2.executeQuery();
                                        if (resultSet2.next()){
                                            try(PreparedStatement preparedStatement3 = connection.prepareStatement(getEmailToSent)){
                                                preparedStatement3.setInt(1, userId);
                                                ResultSet resultSet3 = preparedStatement3.executeQuery();
                                                if(resultSet3.next()){
                                                    String email = resultSet2.getString("Email");
                                                    connection.commit();
                                                    return email;
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
            }catch (Exception e){
                e.printStackTrace();
                connection.rollback();
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public List<String> completeAdoption(int userId, int petId, int userPetId) {
        String updateAdoption = "UPDATE UserPet SET AdoptionDate = NOW() WHERE UserPetId = ?";
        String changeAdoptionStatus = "UPDATE Pet SET AdoptionStatusId = ? WHERE PetId = ?";
        String getAllUsersToNotify = "SELECT UserId FROM UserPet WHERE PetId = ? AND IsFavorite = 1 AND UserId != ?";
        String insertNotification = "INSERT INTO UserNotification(UserId) VALUES (?)";
        String getAllEmailsToSent = "SELECT Email FROM User WHERE UserId = ?";

        List<String> emailsToSend = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateAdoption)) {
                preparedStatement.setInt(1, userPetId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    try (PreparedStatement preparedStatement0 = connection.prepareStatement(changeAdoptionStatus)) {
                        preparedStatement0.setInt(1, AdoptionUtil.ADOPTADO);
                        preparedStatement0.setInt(2, petId);

                        int rowsAffected1 = preparedStatement0.executeUpdate();
                        if (rowsAffected1 > 0) {
                            try (PreparedStatement preparedStatement1 = connection.prepareStatement(getAllUsersToNotify)) {
                                preparedStatement1.setInt(1, petId);
                                preparedStatement1.setInt(2, userId);

                                ResultSet resultSet1 = preparedStatement1.executeQuery();
                                while (resultSet1.next()) {
                                    int userIdToNotify = resultSet1.getInt("UserId");
                                    try (PreparedStatement preparedStatement2 = connection.prepareStatement(insertNotification)) {
                                        preparedStatement2.setInt(1, userIdToNotify);

                                        int rowsAffected2 = preparedStatement2.executeUpdate();
                                        if (rowsAffected2 > 0) {
                                            try (PreparedStatement preparedStatement3 = connection.prepareStatement(getAllEmailsToSent)) {
                                                preparedStatement3.setInt(1, userIdToNotify);

                                                ResultSet resultSet2 = preparedStatement3.executeQuery();
                                                if (resultSet2.next()) {
                                                    String email = resultSet2.getString("Email");
                                                    emailsToSend.add(email);
                                                } else {
                                                    connection.rollback();
                                                    return Collections.emptyList();
                                                }
                                            }
                                        } else {
                                            connection.rollback();
                                            return Collections.emptyList();
                                        }
                                    }
                                }
                            }
                            connection.commit();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        connection.rollback();
                        return Collections.emptyList();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return emailsToSend;
    }


    public List<String> rejectAdoption(int userId, int petId, int userPetId) {
        String updateAdoption = "UPDATE UserPet SET Type = ? WHERE UserPetId = ?";
        String changeAdoptionStatus = "UPDATE Pet SET AdoptionStatusId = ? WHERE PetId = ?";
        String getAllUsersToNotify = "SELECT UserId FROM UserPet WHERE PetId = ? AND IsFavorite = 1 AND UserId != ?";
        String insertNotification = "INSERT INTO UserNotification(UserId) VALUES (?)";
        String getAllEmailsToSent = "SELECT Email FROM User WHERE UserId = ?";

        List<String> emailsToSend = new ArrayList<>();

        try(Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement1 = connection.prepareStatement(updateAdoption)) {
                preparedStatement1.setInt(1, AdoptionUtil.RECHAZAR);
                preparedStatement1.setInt(2, userPetId);

                int rowsAffected = preparedStatement1.executeUpdate();
                if (rowsAffected > 0) {
                    try (PreparedStatement preparedStatement = connection.prepareStatement(changeAdoptionStatus)) {
                        preparedStatement.setInt(1, AdoptionUtil.DISPONIBLE);
                        preparedStatement.setInt(2, petId);

                        int rowsAffected1 = preparedStatement.executeUpdate();
                        if (rowsAffected1 > 0) {
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
                                                    emailsToSend.add(email);
                                                } else {
                                                    connection.rollback();
                                                    return Collections.emptyList();
                                                }
                                            }
                                        } else {
                                            connection.rollback();
                                            return Collections.emptyList();
                                        }
                                    }
                                }
                                connection.commit();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        connection.rollback();
                        return Collections.emptyList();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                connection.rollback();
                return Collections.emptyList();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return emailsToSend;
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

    public String getUserEmail(int userId) {
        String getUserEmail = "SELECT Email FROM User WHERE UserId = ?";
        try(Connection connection = dataSource.getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(getUserEmail)){
                preparedStatement.setInt(1, userId);

                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    return resultSet.getString("Email");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public List<Adoption> getAdoptionsPaginated(int offset, int limit, int statusId) {
        List<Adoption> adoptions = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder(
                "SELECT " +
                    " up.UserPetId, " +
                    " up.UserId, " +
                    " up.PetId, " +
                    " p.Name AS PetName, " +
                    " CONCAT(u.FirstName, ' ', u.LastName) AS UserFullName, " +
                    " COALESCE(up.AdoptionDate, '-') AS AdoptionDate, " +
                    " p.AdoptionStatusId " +
                    "FROM UserPet up " +
                    "JOIN User u ON u.UserId = up.UserId " +
                    "JOIN Pet p ON p.PetId = up.PetId  " +
                "WHERE p.AdoptionStatusId = ? AND up.Type = " + AdoptionUtil.ADOPTAR + " ");

        List<Object> parameters = new ArrayList<>();

        parameters.add(statusId);

        sqlBuilder.append("ORDER BY UserPetId DESC ");
        sqlBuilder.append("LIMIT ? OFFSET ?");
        parameters.add(limit);
        parameters.add(offset);

        String sql = sqlBuilder.toString();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int paramIndex = 1;
            for (Object parameter : parameters) {
                preparedStatement.setObject(paramIndex++, parameter);
            }

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Adoption adoption = new Adoption();
                adoption.setUserPetId(result.getInt("UserPetId"));
                adoption.setUserId(result.getInt("UserId"));
                adoption.setPetId(result.getInt("PetId"));
                adoption.setPetName(result.getString("PetName"));
                adoption.setUserFullName(result.getString("UserFullName"));
                adoption.setAdoptionDate(result.getString("AdoptionDate"));
                adoption.setStatusId(result.getInt("AdoptionStatusId"));
                adoptions.add(adoption);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adoptions;
    }

    public int getTotalCountAdoption(int statusId) {
        int total = 0;
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) AS total FROM UserPet up " +
                "JOIN Pet p ON p.PetId = up.PetId " +
                "WHERE p.AdoptionStatusId = ? AND up.Type = " + AdoptionUtil.ADOPTAR + " ");
        List<Object> parameters = new ArrayList<>();
        parameters.add(statusId);

        String countSql = sqlBuilder.toString();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(countSql)) {
                int paramIndex = 1;
                for (Object parameter : parameters) {
                    preparedStatement.setObject(paramIndex++, parameter);
                }
                ResultSet result = preparedStatement.executeQuery();
                if (result.next()) {
                    total = result.getInt("total");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return total;
    }

}
