package org.grupo12.servlets.Admin.News;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.NewsDAO;
import org.grupo12.models.News;
import org.grupo12.services.implementation.NewsService;
import org.grupo12.util.AuthenticationUtils;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/newsTable")
public class NewsTableServlet extends HttpServlet {
    private HikariDataSource dataSource = ConnectionDB.getDataSource();
    private NewsService newsService;

    @Override
    public void init() {
        newsService = new NewsService(new NewsDAO(dataSource));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{


            //Pass the list of pets to the view
            List<News> newsArr = newsService.getNewsPaginated(request);
            Gson gson = new Gson();
            String newsJson = gson.toJson(newsArr);


            request.setAttribute("news", newsArr);
            request.setAttribute("petsJson", newsJson);

            request.getRequestDispatcher("/WEB-INF/views/admin/news/newsTable.jsp").forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }
}
