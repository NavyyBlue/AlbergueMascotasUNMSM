package org.grupo12.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.grupo12.models.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PetImageDAO {
    private HikariDataSource dataSource;
    public PetImageDAO(HikariDataSource dataSource) {this.dataSource = dataSource;}

    public Image getMainPetImage(int petId) {
        String sql = "SELECT ImageId, PetId, ImageUrl, IsMainImage, Active FROM Image WHERE PetId = ? AND IsMainImage = 1 AND Active = 1";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, petId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Image image = new Image();
                image.setImageId(result.getInt("ImageId"));
                image.setPetId(result.getInt("PetId"));
                image.setImageUrl(result.getString("ImageUrl"));
                image.setMainImage(result.getBoolean("IsMainImage"));
                image.setActive(result.getBoolean("Active"));
                return image;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Image> getPetImages(int petId) {
        String sql = "SELECT ImageId, PetId, ImageUrl, IsMainImage, Active FROM Image WHERE PetId = ? AND Active = 1 AND IsMainImage = 0";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, petId);
            ResultSet result = statement.executeQuery();
            List<Image> images = new ArrayList<>();
            while (result.next()) {
                Image image = new Image();
                image.setImageId(result.getInt("ImageId"));
                image.setPetId(result.getInt("PetId"));
                image.setImageUrl(result.getString("ImageUrl"));
                image.setMainImage(result.getBoolean("IsMainImage"));
                image.setActive(result.getBoolean("Active"));
                images.add(image);
            }
            return images;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean uploadPetImage(int petId, String imageUrl, boolean isMainImage) {
        String sql = "INSERT INTO Image (PetId, ImageUrl, IsMainImage) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, petId);
            statement.setString(2, imageUrl);
            statement.setBoolean(3, isMainImage);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePetImage(int imageId, String imageUrl, boolean Active) {
        String sql = "UPDATE Image SET ImageUrl = ?, Active = ? WHERE ImageId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, imageUrl);
            statement.setBoolean(2, Active);
            statement.setInt(3, imageId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyMainImageExists(int petId) {
        String sql = "SELECT ImageUrl FROM Image WHERE PetId = ? AND IsMainImage = 1 AND Active = 1";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, petId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int hadMainImage(int petId) {
        String sql = "SELECT ImageId FROM Image WHERE PetId = ? AND IsMainImage = 1 AND Active = 0";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, petId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt("ImageId");
            }
            return -1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean deletePetImage(int imageId) {
        String sql = "UPDATE Image SET Active = 0 WHERE ImageId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, imageId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
