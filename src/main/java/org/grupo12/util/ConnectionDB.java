package org.grupo12.util;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionDB {
    private static HikariDataSource dataSource;

    private ConnectionDB() {
    }

    public static HikariDataSource getDataSource() {
        if (dataSource == null) {
            dataSource = HikariCPDataSource.getDataSource();
        }
        return dataSource;
    }
}
