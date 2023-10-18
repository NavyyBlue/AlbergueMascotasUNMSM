package org.grupo12.servlets.Home;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.grupo12.models.User;

import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if (user != null) {
            request.getRequestDispatcher("/WEB-INF/views/home/home.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }
}
