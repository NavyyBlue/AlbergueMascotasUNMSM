package org.grupo12.servlets.ForgotPassword;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/forgotPassword")
 class forgotPasswordSend extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/forgotPassword/forgotPasswordSend.jsp")
                .forward(request, response);
    }
}
