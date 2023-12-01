package org.grupo12.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.grupo12.models.News;
import org.grupo12.models.Pet;
import org.grupo12.util.ImageUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsDAO {
    private HikariDataSource dataSource;

    public NewsDAO(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean insertNews(News news) {
        String insertNewsSQL = "INSERT INTO News " +
                "(title, description, creationDate, userId, newsType, active, image) " +
                "VALUES (?, ?, CURRENT_TIMESTAMP, ?,?, 1,?)";


        Connection connection = null;
        PreparedStatement newsStatement = null;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);  // Iniciar transacción

            newsStatement = connection.prepareStatement(insertNewsSQL, Statement.RETURN_GENERATED_KEYS);

            newsStatement.setString(1, news.getTitle());
            newsStatement.setString(2, news.getDescription());
            newsStatement.setInt(3, news.getUserId());
            newsStatement.setInt(4, news.getNewsType());
            newsStatement.setString(5, news.getImage());

            int rowsUpdated = newsStatement.executeUpdate();

            if (rowsUpdated > 0) {
                // Obtener el ID generado para la noticia
                ResultSet generatedKeys = newsStatement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    int newsId = generatedKeys.getInt(1);

                    connection.commit();
                    return true;
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
                if (newsStatement != null) {
                    newsStatement.close();
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

    public List<News> getNews(int newsId, int active, int offset, int limit) {
        List<News> newsArr = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT " +
                "    n.NewsId, " +
                "    n.Title, " +
                "    n.Description, " +
                "    n.CreationDate, " +
                "    n.UserId, " +
                "    n.NewsType, " +
                "    n.Active " +
                "FROM News n ");

        List<Object> parameters = new ArrayList<>();

        if (active != 2) {
            sqlBuilder.append("WHERE Active = ? ");
            parameters.add(active);
        } else {
            sqlBuilder.append("WHERE Active = 1 OR Active = 0 ");
        }

        if (newsId != 0) {
            sqlBuilder.append("AND NewsId = ? ");
            parameters.add(newsId);
        }

        // Add limit and offset after WHERE clause
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
                News news = new News();
                news.setNewsId(result.getInt("NewsId"));
                news.setTitle(result.getString("Title"));
                news.setDescription(result.getString("Description"));
                news.setCreationDate(result.getString("CreationDate"));
                news.setUserId(result.getInt("UserId"));
                news.setNewsType(result.getInt("NewsType"));
                news.setActive(result.getInt("Active"));
                newsArr.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsArr;
    }

    public int getTotalNewsCount(String searchKeyword) {
        int total = 0;
        StringBuilder sqlBuilder = new StringBuilder( "SELECT COUNT(*) AS total FROM Pet WHERE AdoptionStatusId = 1 AND Active = 1 ");

        List<Object> parameters = new ArrayList<>();


        // Add searchKeyword filter
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            sqlBuilder.append("AND Tittle LIKE ? ");
            parameters.add("%" + searchKeyword + "%");
        }

        String countSql = sqlBuilder.toString();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement countStatement = connection.prepareStatement(countSql)) {

            ResultSet countResult = countStatement.executeQuery();

            if (countResult.next()) {
                total = countResult.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    public boolean updateNews(News news) {
        String sql = "UPDATE News SET Tittle = ?, Description = ?, Image= ?, CreationDate = ? ,NewsType = ? WHERE NewsId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, news.getTitle());
            statement.setString(2, news.getDescription());
            statement.setString(3, news.getImage());
            statement.setString(8, news.getCreationDate());
            statement.setInt(9, news.getNewsType());
            statement.setInt(10, news.getNewsId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteNews(int newsId){
        String sql = "UPDATE News SET Active = 0 WHERE NewsId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newsId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean restoreNews(int newsId){
        String sql = "UPDATE News SET Active = 1 WHERE NewsId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newsId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
