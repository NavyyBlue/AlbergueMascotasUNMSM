package org.grupo12.servlets.Error;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/error")
public class ErrorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isError = request.getSession().getAttribute("errorOccurred") != null;
        if(!isError){
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/error/error.jsp")
                .forward(request, response);
    }
}
