package org.grupo12.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPDataSource {
    private static HikariDataSource dataSource;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://35.224.219.122:3306/ALBERGUE_MASCOTAS";
    private static final String USER = "root";
    private static final String PASSWORD = "gta5elmejor";
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setDriverClassName(DRIVER);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }
    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}
