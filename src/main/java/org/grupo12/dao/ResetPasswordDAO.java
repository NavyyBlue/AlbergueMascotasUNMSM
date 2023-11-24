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

    public String GenerateUniqueOTP(){
        String otp;
        do{
            otp = PasswordEncryptionUtil.generateOTP();
        }while (OTPCodeExits(otp));
        return otp;
    }

    public boolean OTPCodeExits(String otp){
        String sql = "SELECT * FROM ResetPassword WHERE Otp = ?";
        boolean resp = false;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, otp);
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
        String SELECT_RESET_PASSWORD_QUERY = "SELECT ResetPasswordId, UserId, ExpirationDate FROM ResetPassword WHERE Otp = ? LIMIT 1";
        String UPDATE_USER_PASSWORD_QUERY = "UPDATE User SET Password = ? WHERE UserId = ?";
        String UPDATE_EXPIRED_RESET_PASSWORD_QUERY = "UPDATE ResetPassword SET ExpirationDate = NOW() WHERE ResetPasswordId = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement selectResetPasswordStatement = connection.prepareStatement(SELECT_RESET_PASSWORD_QUERY);
            PreparedStatement updateUserPasswordStatement = connection.prepareStatement(UPDATE_USER_PASSWORD_QUERY);
            PreparedStatement updateExpiredResetPasswordStatement = connection.prepareStatement(UPDATE_EXPIRED_RESET_PASSWORD_QUERY)){


            selectResetPasswordStatement.setString(1, otp);
            ResultSet selectResetPasswordResult = selectResetPasswordStatement.executeQuery();

            if(selectResetPasswordResult.next()){

                int resetPasswordId = selectResetPasswordResult.getInt("ResetPasswordId");
                int userId = selectResetPasswordResult.getInt("UserId");
                Timestamp expirationDate = selectResetPasswordResult.getTimestamp("ExpirationDate");
                Timestamp now = new Timestamp(System.currentTimeMillis());
                if(expirationDate.after(now)){
                    updateUserPasswordStatement.setString(1, newPassword);
                    updateUserPasswordStatement.setInt(2, userId);
                    updateUserPasswordStatement.executeUpdate();

                    updateExpiredResetPasswordStatement.setInt(1, resetPasswordId);
                    updateExpiredResetPasswordStatement.executeUpdate();

                    return true;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
