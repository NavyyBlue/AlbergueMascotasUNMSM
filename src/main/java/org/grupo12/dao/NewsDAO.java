package org.grupo12.dao;

import com.zaxxer.hikari.HikariDataSource;

public class NewsDAO {
    private HikariDataSource dataSource;

    public NewsDAO(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }


}
