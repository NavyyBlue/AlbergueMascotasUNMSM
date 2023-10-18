package org.grupo12.servlets.Login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.grupo12.models.User;
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
        String username = request.getParameter("username");
        String pass = request.getParameter("password");

        Connection connection = ConnectionDB.getConnection();

        String sql = "SELECT * FROM User WHERE UserName = ? AND Password = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, pass);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                User user = new User();
                user.setUserId(result.getInt("UserId"));
                user.setFirstName(result.getString("FirstName"));
                user.setLastName(result.getString("LastName"));
                user.setEmail(result.getString("Email"));
                user.setUserImage(result.getString("UserImage"));
                user.setUserName(result.getString("UserName"));
                user.setPhoneNumber(result.getString("PhoneNumber"));
                user.setActive(result.getBoolean("Active"));

                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                response.sendRedirect("login");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
