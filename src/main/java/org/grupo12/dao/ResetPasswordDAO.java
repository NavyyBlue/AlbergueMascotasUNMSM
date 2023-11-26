package org.grupo12.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.grupo12.util.PasswordEncryptionUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class ResetPasswordDAO {
    private HikariDataSource dataSource;

    public ResetPasswordDAO(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String GenerateUniqueOTP() throws NoSuchAlgorithmException {
        String otp;
        do{
            otp = PasswordEncryptionUtil.generateOTP();
        }while (OTPCodeExits(otp));
        return otp;
    }

    public boolean OTPCodeExits(String otp) throws NoSuchAlgorithmException {
        String hashOtp = PasswordEncryptionUtil.hashOTP(otp);
        String sql = "SELECT * FROM ResetPassword WHERE Otp = ?";
        boolean resp = false;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, hashOtp);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                resp = true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return resp;
    }

    public String generateOTPForUser(String email) throws NoSuchAlgorithmException {
        String CHECK_USER_QUERY  = "SELECT UserId FROM User WHERE Email = ? LIMIT 1";

        String GET_ACTIVE_RESET_PASSWORD_QUERY = "SELECT ResetPasswordId FROM ResetPassword " +
                "WHERE UserId = ? AND ExpirationDate > NOW() ORDER BY ExpirationDate DESC LIMIT 1";

        String UPDATE_EXPIRED_RESET_PASSWORD_QUERY = "UPDATE ResetPassword SET ExpirationDate = NOW() " +
                "WHERE ResetPasswordId = ?";

        String INSERT_RESET_PASSWORD_QUERY = "INSERT INTO ResetPassword (UserId, Otp, CreationDate, ExpirationDate) " +
                "VALUES (?, ?, NOW(), DATE_ADD(NOW(), INTERVAL 10 MINUTE))";

        String otp = GenerateUniqueOTP();
        String hashedOtp = PasswordEncryptionUtil.hashOTP(otp);
        int updateResult = 0;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement checkUserStatement = connection.prepareStatement(CHECK_USER_QUERY);
            PreparedStatement getActiveResetPasswordStatement = connection.prepareStatement(GET_ACTIVE_RESET_PASSWORD_QUERY);
            PreparedStatement updateExpiredResetPasswordStatement = connection.prepareStatement(UPDATE_EXPIRED_RESET_PASSWORD_QUERY);
            PreparedStatement insertResetPasswordStatement = connection.prepareStatement(INSERT_RESET_PASSWORD_QUERY)){

            checkUserStatement.setString(1, email);
            ResultSet checkUserResult = checkUserStatement.executeQuery();

            if(checkUserResult.next()) {
                int userId = checkUserResult.getInt("UserId");

                getActiveResetPasswordStatement.setInt(1, userId);
                ResultSet getActiveResetPasswordResult = getActiveResetPasswordStatement.executeQuery();

                int updateExpiredResult = 0;
                if (getActiveResetPasswordResult.next()) {
                    int resetPasswordId = getActiveResetPasswordResult.getInt("ResetPasswordId");

                    updateExpiredResetPasswordStatement.setInt(1, resetPasswordId);
                    updateExpiredResult = updateExpiredResetPasswordStatement.executeUpdate();
                }

                insertResetPasswordStatement.setInt(1, userId);
                insertResetPasswordStatement.setString(2, hashedOtp);
                int insertResult = insertResetPasswordStatement.executeUpdate();

                if (updateExpiredResult > 0 && insertResult > 0) {
                    updateResult = 1;
                }
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return updateResult > 0 ? otp : null;
    }

    public boolean verifyOTP(String otp){
        String sql = "SELECT * FROM ResetPassword WHERE Otp = ? AND ExpirationDate > NOW()";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, otp);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(String otp, String newPassword){
        String UPDATE_PASSWORD_QUERY = "UPDATE User " +
                "SET Password = ? " +
                "WHERE UserId = (SELECT UserId FROM ResetPassword WHERE Otp = ? AND ExpirationDate > NOW() LIMIT 1)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement updatePasswordStatement = connection.prepareStatement(UPDATE_PASSWORD_QUERY)) {

            updatePasswordStatement.setString(1, newPassword);
            updatePasswordStatement.setString(2, otp);

            int rowsAffected = updatePasswordStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
