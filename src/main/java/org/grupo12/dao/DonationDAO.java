package org.grupo12.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.grupo12.models.Donation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonationDAO {
    private HikariDataSource dataSource;

    public DonationDAO(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Donation> getDonationsPaginated(int donationId, int active, int offset, int limit) {
        List<Donation> donations = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder(
                "SELECT" +
                " DonationId, " +
                " FullNameDonator, " +
                " PhoneNumerDonator, " +
                " Amount, " +
                " MethodPaymentId, " +
                " IFNULL(PaymentDate, '-') as PaymentDate, " +
                " Active " +
                "FROM Donation ");

        List<Object> parameters = new ArrayList<>();

        if(active != 2){
            sqlBuilder.append("WHERE Active = ? ");
            parameters.add(active);
        }else {
            sqlBuilder.append("WHERE Active = 1 OR Active = 0 ");
        }

        if(donationId != 0){
            sqlBuilder.append("AND DonationId = ? ");
            parameters.add(donationId);
        }
        // Add limit and offset
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
                Donation donation = new Donation();
                donation.setDonationId(result.getInt("DonationId"));
                donation.setFullNameDonator(result.getString("FullNameDonator"));
                donation.setPhoneNumberDonator(result.getString("PhoneNumerDonator"));
                donation.setAmount(result.getFloat("Amount"));
                donation.setMethodPaymentId(result.getInt("MethodPaymentId"));
                donation.setPaymentDate(result.getString("PaymentDate"));
                donation.setActive(result.getBoolean("Active"));
                donations.add(donation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donations;
    }

    public int getTotalCountDonation(int active){
        int total = 0;
        StringBuilder sqlBuilder = new StringBuilder( "SELECT COUNT(*) AS total FROM Donation ");

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

    public boolean addDonation(Donation donation) {
        String sql = "INSERT INTO Donation (FullNameDonator, PhoneNumerDonator, Amount, MethodPaymentId) VALUES (?, ?, ?, ?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, donation.getFullNameDonator());
            preparedStatement.setString(2, donation.getPhoneNumberDonator());
            preparedStatement.setFloat(3, donation.getAmount());
            preparedStatement.setInt(4, donation.getMethodPaymentId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateDonation(Donation donation) {
        String sql = "UPDATE Donation SET FullNameDonator = ?, PhoneNumerDonator = ?, Amount = ?, MethodPaymentId = ?, PaymentDate = ? WHERE DonationId = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, donation.getFullNameDonator());
            preparedStatement.setString(2, donation.getPhoneNumberDonator());
            preparedStatement.setFloat(3, donation.getAmount());
            preparedStatement.setInt(4, donation.getMethodPaymentId());
            preparedStatement.setString(5, donation.getPaymentDate());
            preparedStatement.setInt(6, donation.getDonationId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDonation(int donationId) {
        String sql = "UPDATE Donation SET Active = 0 WHERE DonationId = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, donationId);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean restoreDonation(int donationId) {
        String sql = "UPDATE Donation SET Active = 1 WHERE DonationId = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, donationId);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
