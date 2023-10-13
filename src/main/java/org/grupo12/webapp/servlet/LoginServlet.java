package org.grupo12.webapp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.PreparedStatement;
import java.io.IOException;
import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/home")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        getServletContext()
                .getRequestDispatcher("/views/home/home.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");

        if ("user".equals(usuario) && "con".equals(contrasena)) {
            // Autenticación exitosa
            HttpSession session = request.getSession();
            session.setAttribute("nuevoTexto", usuario);
            response.sendRedirect(request.getContextPath() + "/bienvenido"); // Redirige a una página de bienvenida
        } else {
            // Autenticación fallida
            response.sendRedirect("bienvenido.jsp"); // Redirige a una página de error
        }
    }
}
