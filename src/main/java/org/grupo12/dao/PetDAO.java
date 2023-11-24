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

    public void insertPet(String name, String age, String gender, String description, String speciesId, String breed, String location) {
        String sql = "INSERT INTO Pet " +
                "(Name, Age, SpeciesId, Gender, Description, EntryDate, AdoptionStatusId, Active, Breed, Location) " +
                "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, 1, 1, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, Integer.parseInt(age));
            statement.setInt(3, Integer.parseInt(speciesId));
            statement.setString(4, gender);
            statement.setString(5, description);
            statement.setString(6, breed);
            statement.setInt(7, Integer.parseInt(location));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Pet> getPetListBySpecies(int speciesId, int age, String gender, String searchKeyword, int offset, int limit) {
        List<Pet> pets = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT " +
                "    p.PetId, " +
                "    p.Name, " +
                "    p.Age, " +
                "    p.Age, " +
                "    p.Gender, " +
                "    p.Breed, " +
                "    p.EntryDate, " +
                "    p.Location, " +
                "    img.ImageUrl " +
                "FROM Pet p " +
                "LEFT JOIN Image img ON img.PetId = p.PetId " +
                "WHERE p.AdoptionStatusId = 1 AND p.Active = 1 ");

        List<Object> parameters = new ArrayList<>();

        // Add speciesId filter
        if (speciesId != 0) {
            sqlBuilder.append("AND p.SpeciesId = ? ");
            parameters.add(speciesId);
        }

        // Add age filter
        if (age > 0) {
            sqlBuilder.append("AND p.Age = ? ");
            parameters.add(age);
        }

        // Add gender filter
        if (gender != null && !gender.isEmpty()) {
            sqlBuilder.append("AND p.Gender = ? ");
            parameters.add(gender);
        }

        // Add searchKeyword filter
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            sqlBuilder.append("AND p.Name LIKE ? ");
            parameters.add("%" + searchKeyword + "%");
        }

        // Add limit and offset
        sqlBuilder.append("LIMIT ? OFFSET ?");
        parameters.add(limit);
        parameters.add(offset);

        String sql = sqlBuilder.toString();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set parameters
            int paramIndex = 1;
            for (Object parameter : parameters) {
                statement.setObject(paramIndex++, parameter);
            }

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

    public int getTotalPetCount(int speciesId, int age, String gender, String searchKeyword) {
        int total = 0;
        StringBuilder sqlBuilder = new StringBuilder( "SELECT COUNT(*) AS total FROM Pet WHERE AdoptionStatusId = 1 AND Active = 1 ");

        List<Object> parameters = new ArrayList<>();
        // Add speciesId filter
        if (speciesId != 0) {
            sqlBuilder.append("AND SpeciesId = ? ");
            parameters.add(speciesId);
        }

        // Add age filter
        if (age > 0) {
            sqlBuilder.append("AND Age = ? ");
            parameters.add(age);
        }

        // Add gender filter
        if (gender != null && !gender.isEmpty()) {
            sqlBuilder.append("AND Gender = ? ");
            parameters.add(gender);
        }

        // Add searchKeyword filter
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            sqlBuilder.append("AND Name LIKE ? ");
            parameters.add("%" + searchKeyword + "%");
        }

        String countSql = sqlBuilder.toString();

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
//                String locationName = getLocationName(locationValue);
//                pet.setLocation(locationName);
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
