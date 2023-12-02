package org.grupo12.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.grupo12.models.Pet;
import org.grupo12.models.User;
import org.grupo12.util.ConnectionDB;
import org.grupo12.util.ImageUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetDAO {
    private HikariDataSource dataSource;

    public PetDAO(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean insertPet(Pet pet) {
        String insertPetSQL = "INSERT INTO Pet " +
                "(Name, Age, SpeciesId, Gender, Description, EntryDate, Breed, AdoptionStatusId, Location) " +
                "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?)";

        String insertImageSQL = "INSERT INTO Image " +
                "(PetId, ImageUrl, IsMainImage) " +
                "VALUES (?, ?, 1)";

        Connection connection = null;
        PreparedStatement petStatement = null;
        PreparedStatement imageStatement = null;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);  // Iniciar transacción

            petStatement = connection.prepareStatement(insertPetSQL, Statement.RETURN_GENERATED_KEYS);

            petStatement.setString(1, pet.getName());
            petStatement.setInt(2, pet.getAge());
            petStatement.setInt(3, pet.getSpeciesId());
            petStatement.setString(4, pet.getGender());
            petStatement.setString(5, pet.getDescription());
            petStatement.setString(6, pet.getBreed());
            petStatement.setInt(7, pet.getAdoptionStatusId());
            petStatement.setInt(8, pet.getLocation());

            int rowsUpdated = petStatement.executeUpdate();

            if (rowsUpdated > 0) {
                // Obtener el ID generado para la mascota
                ResultSet generatedKeys = petStatement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    int petId = generatedKeys.getInt(1);

                    // Preparar la segunda consulta para la imagen
                    imageStatement = connection.prepareStatement(insertImageSQL);
                    imageStatement.setInt(1, petId);
                    imageStatement.setString(2, ImageUtil.defaultPath);

                    // Ejecutar la segunda consulta
                    int imageRowsUpdated = imageStatement.executeUpdate();

                    if (imageRowsUpdated > 0) {
                        // Ambas consultas se ejecutaron correctamente, realizar commit
                        connection.commit();
                        return true;
                    }
                }
            }

            //realizar rollback para deshacer cambios en caso de error
            connection.rollback();
            return false;

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            e.printStackTrace();
            return false;

        } finally {
            try {
                if (petStatement != null) {
                    petStatement.close();
                }
                if (imageStatement != null) {
                    imageStatement.close();
                }
                if (connection != null) {
                    connection.setAutoCommit(true);  // Restaurar comportamiento por defecto
                    connection.close();
                }
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }


    public List<Pet> getPets (int petId, int active, int offset, int limit){
        List<Pet> pets = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT " +
                "    p.PetId, " +
                "    p.Name, " +
                "    COALESCE(p.Age, '-') AS Age, " +
                "    p.Age, " +
                "    p.SpeciesId, " +
                "    p.Gender, " +
                "    COALESCE(p.Breed, '-') AS Breed, " +
                "    p.Description, " +
                "    p.EntryDate, " +
                "    p.AdoptionStatusId, " +
                "    p.Location, " +
                "    p.Active " +
                "FROM Pet p ");

        List<Object> parameters = new ArrayList<>();
        if(active != 2){
            sqlBuilder.append("WHERE Active = ? ");
            parameters.add(active);
        }else {
            sqlBuilder.append("WHERE Active = 1 OR Active = 0 ");
        }

        if(petId != 0){
            sqlBuilder.append("AND PetId = ? ");
            parameters.add(petId);
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
                pet.setSpeciesId(result.getInt("SpeciesId"));
                pet.setGender(result.getString("Gender"));
                pet.setBreed(result.getString("Breed"));
                pet.setDescription(result.getString("Description"));
                pet.setEntryDate(result.getString("EntryDate"));
                pet.setAdoptionStatusId(result.getInt("AdoptionStatusId"));
                pet.setLocation(result.getInt("Location"));
                pet.setActive(result.getInt("Active"));
                pets.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }


    public List<Pet> getPetListBySpecies(int speciesId, int age, String gender, String searchKeyword, int offset, int limit) {
        List<Pet> pets = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT " +
                "    p.PetId, " +
                "    p.Name, " +
                "    COALESCE(p.Age, '-') AS Age, " +
                "    p.Age, " +
                "    p.SpeciesId, " +
                "    p.Gender, " +
                "    COALESCE(p.Breed, '-') AS Breed, " +
                "    p.Description, " +
                "    p.EntryDate, " +
                "    p.AdoptionStatusId, " +
                "    p.Location, " +
                "    img.ImageUrl, " +
                "    img.IsMainImage, " +
                "    img.Active AS IsImageActive, " +
                "    p.Active " +
                "FROM Pet p " +
                "LEFT JOIN Image img ON img.PetId = p.PetId " +
                "WHERE p.AdoptionStatusId = 1 AND p.Active = 1 AND img.IsMainImage = 1 ");


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
                pet.setSpeciesId(result.getInt("SpeciesId"));
                pet.setGender(result.getString("Gender"));
                pet.setBreed(result.getString("Breed"));
                pet.setDescription(result.getString("Description"));
                pet.setEntryDate(result.getString("EntryDate"));
                pet.setAdoptionStatusId(result.getInt("AdoptionStatusId"));
                pet.setLocation(result.getInt("Location"));
                pet.setImageUrl(result.getString("ImageUrl"));
                pet.setMainImage(result.getBoolean("IsMainImage"));
                pet.setImageActive(result.getBoolean("IsImageActive"));
                pet.setActive(result.getInt("Active"));
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
                    " img.ImageUrl, " +
                    " img.IsMainImage, " +
                    " img.Active AS IsImageActive " +
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
                pet.setMainImage(result.getBoolean("IsMainImage"));
                pet.setImageActive(result.getBoolean("IsImageActive"));
                petImages.add(pet);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return petImages;
    }

        public boolean updatePet(Pet pet){
        String sql = "UPDATE Pet SET Name = ?, Age = ?, SpeciesId = ?, Gender = ?, Description = ?, Breed = ?, Location = ?, EntryDate = ? ,AdoptionStatusId = ? WHERE PetId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pet.getName());
            statement.setInt(2, pet.getAge());
            statement.setInt(3, pet.getSpeciesId());
            statement.setString(4, pet.getGender());
            statement.setString(5, pet.getDescription());
            statement.setString(6, pet.getBreed());
            statement.setInt(7, pet.getLocation());
            statement.setString(8, pet.getEntryDate());
            statement.setInt(9, pet.getAdoptionStatusId());
            statement.setInt(10, pet.getPetId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePet(int petId){
        String sql = "UPDATE Pet SET Active = 0 WHERE PetId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, petId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean restorePet(int petId){
        String sql = "UPDATE Pet SET Active = 1 WHERE PetId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, petId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
