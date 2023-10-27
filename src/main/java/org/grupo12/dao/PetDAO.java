package org.grupo12.dao;

import org.grupo12.models.Pet;
import org.grupo12.util.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetDAO {
    public List<Pet> getPetListBySpecies(int speciesId, int offset, int limit) {
        List<Pet> pets = new ArrayList<>();
        Connection connection = ConnectionDB.getConnection();
        String sql = "SELECT " +
                "    p.PetId, " +
                "    p.Name, " +
                "    p.Age, " +
                "    p.Gender, " +
                "    pimg.ImageUrl " +
                "FROM Pet p " +
                "LEFT JOIN PetImage pimg ON pimg.PetId = p.PetId " +
                "WHERE p.AdoptionStatusId = 1 " +
                (speciesId == 0 ? "" : "AND p.SpeciesId = ? ") +
                "AND p.Active = 1 " +
                "LIMIT ? OFFSET ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
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

    public List<Pet> getPetListBySpecies(int offset, int limit) {
        return getPetListBySpecies(0, offset, limit);
    }

    public int getTotalPetCount(int speciesId) {
        int total = 0;
        Connection connection = ConnectionDB.getConnection();
        String countSql = "SELECT COUNT(*) AS total FROM Pet WHERE AdoptionStatusId = 1 " +
                        (speciesId == 0 ? "" : "AND SpeciesId = ?");

        try {
            PreparedStatement countStatement = connection.prepareStatement(countSql);
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
}
