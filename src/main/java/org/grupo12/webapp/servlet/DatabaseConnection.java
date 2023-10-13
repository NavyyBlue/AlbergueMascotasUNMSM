package org.grupo12.webapp.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String jdbcURL = "jdbc:mysql://35.224.219.122:3306/";
    private static final String dbUser = "root";
    private static final String dbPassword = "gta5elmejor";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
