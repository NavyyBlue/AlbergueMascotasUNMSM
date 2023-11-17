package org.grupo12.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.grupo12.models.Pet;
import org.grupo12.util.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetDAO {
    private HikariDataSource dataSource;

    public PetDAO(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Pet> getPetListBySpecies(int speciesId, int offset, int limit) {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT " +
                "    p.PetId, " +
                "    p.Name, " +
                "    p.Age, " +
                "    p.Gender, " +
                "    img.ImageUrl " +
                "FROM Pet p " +
                "LEFT JOIN Image img ON img.PetId = p.PetId " +
                "WHERE p.AdoptionStatusId = 1 " +
                (speciesId == 0 ? "" : "AND p.SpeciesId = ? ") +
                "AND p.Active = 1 " +
                "LIMIT ? OFFSET ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            int paramIndex = 1;
            if (speciesId != 0) {
                statement.setInt(paramIndex++, speciesId);
            }
            statement.setInt(paramIndex++, limit);
            statement.setInt(paramIndex, offset);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Pet pet = new Pet();
                pet.setPetId(result.getInt("PetId"));
                pet.setName(result.getString("Name"));
                pet.setAge(result.getInt("Age"));
                pet.setGender(result.getString("Gender"));
                pet.setImageUrl(result.getString("ImageUrl"));
                pets.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }

    public int getTotalPetCount(int speciesId) {
        int total = 0;
        String countSql = "SELECT COUNT(*) AS total FROM Pet WHERE AdoptionStatusId = 1 " +
                (speciesId == 0 ? "" : "AND SpeciesId = ?");

        try (Connection connection = dataSource.getConnection();
             PreparedStatement countStatement = connection.prepareStatement(countSql)) {
            if (speciesId != 0) {
                countStatement.setInt(1, speciesId);
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

    public Pet getPetInfo(int petId){
        Pet pet = new Pet();
        String sql = "SELECT " +
                    " Name, " +
                    " Age, " +
                    " Gender, " +
                    " Breed, " +
                    " Location, " +
                    " Description " +
                    "FROM Pet " +
                    "WHERE PetId = ? AND Active = 1 ";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, petId);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                pet.setName(result.getString("Name"));
                pet.setAge(result.getInt("Age"));
                pet.setGender(result.getString("Gender"));
                pet.setDescription(result.getString("Description"));
                pet.setBreed(result.getString("Breed"));
                //Mapeo de los nombres de las ubicaciones
                int locationValue = result.getInt("Location");
                String locationName = getLocationName(locationValue);
                pet.setLocation(locationName);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return pet;
    }

    //Metodo para mapear los nombres de las ubicaciones
    private String getLocationName(int locationValue) {
        Map<Integer, String> locationMap = new HashMap<>();
        locationMap.put(0, "Ciudad Universitaria");
        locationMap.put(1, "San Fernando");
        locationMap.put(2, "Veterinaria");

        return locationMap.getOrDefault(locationValue, "Desconocido");
    }

    public List<Pet> getPetStatus(int petId){
        List<Pet> petStatus = new ArrayList<>();
        String sql = "SELECT " +
                    " ps.PetStatusId, " +
                    " ps.StatusName " +
                    "FROM Pet p " +
                    "JOIN PetPetStatus pps ON pps.PetId = p.PetId " +
                    "JOIN PetStatus ps ON ps.PetStatusId = pps.PetStatusId " +
                    "WHERE p.PetId = ? AND p.Active = 1";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, petId);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Pet pet = new Pet();
                pet.setPetStatusName(result.getString("StatusName"));
                petStatus.add(pet);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return petStatus;
    }

    public List<Pet> getPetImages(int petId){
        List<Pet> petImages = new ArrayList<>();
        String sql = "SELECT " +
                    " img.ImageId, " +
                    " img.ImageUrl " +
                    "FROM Pet p " +
                    "JOIN Image img ON img.PetId = p.PetId " +
                    "WHERE p.PetId = ? AND p.Active = 1 AND img.Active = 1 ";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, petId);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Pet pet = new Pet();
                pet.setImageUrl(result.getString("ImageUrl"));
                petImages.add(pet);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return petImages;
    }
}
