package org.grupo12.servlets.Admin.News;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.NewsDAO;
import org.grupo12.services.implementation.NewsService;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;

@WebServlet("/admin/news/deleteImage")
public class DeleteImageServlet extends HttpServlet {
    private HikariDataSource dataSource = ConnectionDB.getDataSource();
    private final NewsService newsService;

    public DeleteImageServlet() {
        this.newsService = new NewsService(new NewsDAO(dataSource));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


}
