package org.grupo12.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.grupo12.models.Pet;
import org.grupo12.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private HikariDataSource dataSource;
    public ProductDAO(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Product> getProducts (int productId, int active, int offset, int limit){
        List<Product> products = new ArrayList<>();

        StringBuilder sqlBuilder = new StringBuilder("SELECT " +
                "    ProductId, " +
                "    Name, " +
                "    Price, " +
                "    Stock, " +
                "    Description, " +
                "    Category, " +
                "    Active " +
                "FROM Products ");
        List<Object> parameters = new ArrayList<>();
        if(active != 2){
            sqlBuilder.append("WHERE Active = ? ");
            parameters.add(active);
        }else {
            sqlBuilder.append("WHERE Active = 1 OR Active = 0 ");
        }

        if(productId != 0){
            sqlBuilder.append("AND ProductId = ? ");
            parameters.add(productId);
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
                Product product = new Product();
                product.setProductId(result.getInt("ProductId"));
                product.setName(result.getString("Name"));
                product.setPrice(result.getInt("Price"));
                product.setStock(result.getInt("Stock"));
                product.setDescription(result.getString("Description"));
                product.setCategory(result.getInt("Category"));
                product.setActive(result.getInt("Active"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public int getTotalCountProducts(int active){
        int total = 0;
        StringBuilder sqlBuilder = new StringBuilder( "SELECT COUNT(*) AS total FROM Products ");
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
}
