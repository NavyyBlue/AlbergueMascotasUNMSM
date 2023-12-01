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

    public boolean createProduct(Product product){
        String sql = "INSERT INTO Products " +
                "(Name, Price, Stock, Description, Category, Active) " +
                "VALUES(?, ?, ?, ?, ?, 1);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getStock());
            statement.setString(4, product.getDescription());
            statement.setInt(5, product.getCategory());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

    public boolean updateProduct(Product product){
        String sql = "UPDATE Products " +
                "SET Name= ?, Price= ?, Stock= ?, Description= ?, Category= ?, Active= 1 WHERE ProductId= ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getStock());
            statement.setString(4, product.getDescription());
            statement.setInt(5, product.getCategory());
            statement.setInt(6, product.getProductId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(int productId){
        String sql = "UPDATE Products SET Active = 0 WHERE ProductId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean restoreProduct(int productId){
        String sql = "UPDATE Products SET Active = 1 WHERE ProductId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
