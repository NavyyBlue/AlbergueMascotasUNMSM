package org.grupo12.servlets.Admin;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import org.grupo12.util.ConnectionDB;

@WebServlet("/pettable")
public class PetTableServlet extends HttpServlet {
    private HikariDataSource dataSource = ConnectionDB.getDataSource();

}
