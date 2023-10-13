package org.grupo12.servlets.Login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/Login/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        Connection connection = ConnectionDB.getConnection();

        String sql = "SELECT * FROM User WHERE UserName = ? AND Password = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user);
            statement.setString(2, pass);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("username", user);
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                response.sendRedirect("login");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
