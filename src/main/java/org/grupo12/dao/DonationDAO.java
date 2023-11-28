package org.grupo12.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.grupo12.models.Donation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DonationDAO {
    private HikariDataSource dataSource;

    public DonationDAO(HikariDataSource dataSource) {
        this.dataSource = dataSource;
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
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
