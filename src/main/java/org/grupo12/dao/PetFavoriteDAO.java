package org.grupo12.dao;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetFavoriteDAO {
    private HikariDataSource dataSource;

    public PetFavoriteDAO(HikariDataSource dataSource) {this.dataSource = dataSource;}

    public boolean addFavorite(int userId, int petId) {
        String sql = "INSERT INTO UserPet (UserId, PetId, IsFavorite)VALUES(?, ?, 1)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, petId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFavorite(int userId, int petId) {
        String sql = "UPDATE UserPet SET IsFavorite = 0 WHERE UserId = ? AND PetId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, petId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFavorite(int userId, int petId, int isFavorite) {
        String sql = "UPDATE UserPet SET IsFavorite = ? WHERE UserId = ? AND PetId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, isFavorite);
            statement.setInt(2, userId);
            statement.setInt(3, petId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int isFavorite(int userId, int petId) {
        String sql = "SELECT IsFavorite FROM UserPet WHERE UserId = ? AND PetId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, petId);

            int isFavorite = 2;
            var result = statement.executeQuery();

            if (result.next()) {
                isFavorite = result.getInt("IsFavorite");
            }
            return isFavorite;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Integer> getFavoritePetsByUser(int userId) {
        String sql = "SELECT PetId FROM UserPet WHERE UserId = ? AND IsFavorite = 1";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);

            var result = statement.executeQuery();
            List<Integer> pets = new ArrayList<>();
            while (result.next()) {
                pets.add(result.getInt("PetId"));
            }
            return pets;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getTotalFavoritesByPet(int petId) {
        String sql = "SELECT COUNT(*) AS Total FROM UserPet WHERE PetId = ? AND IsFavorite = 1";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, petId);

            var result = statement.executeQuery();
            int total = 0;
            if (result.next()) {
                total = result.getInt("Total");
            }
            return total;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
