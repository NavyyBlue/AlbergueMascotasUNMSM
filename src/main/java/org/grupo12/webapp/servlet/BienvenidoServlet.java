package org.grupo12.webapp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/bienvenido")
public class BienvenidoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        String nuevoTexto = (String) httpServletRequest.getSession().getAttribute("nuevoTexto");
        httpServletRequest.setAttribute("nuevoTexto", nuevoTexto);
        getServletContext()
                .getRequestDispatcher("/views/bienvenido.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }
}
