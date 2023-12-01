package org.grupo12.servlets.Admin.Adoption;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.AdoptionDAO;
import org.grupo12.models.Adoption;
import org.grupo12.models.Donation;
import org.grupo12.services.implementation.AdoptionService;
import org.grupo12.util.AuthenticationUtils;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/adoptionsTable")
public class AdoptionsTableServlet extends HttpServlet {
    private final HikariDataSource dataSource = ConnectionDB.getDataSource();

    private AdoptionService adoptionService;

    @Override
    public void init() {
        adoptionService = new AdoptionService(new AdoptionDAO(dataSource));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            // Verificar la autorización de administrador
            if (!AuthenticationUtils.isAuthenticatedAsAdmin(request, response)) {
                return;
            }

            List<Adoption> adoptions = adoptionService.getAdoptionsPaginated(request);

            request.setAttribute("adoptions", adoptions);

            request.getRequestDispatcher("/WEB-INF/views/admin/adoption/adoptiontable.jsp").forward(request, response);
        }catch (Exception e) {
            request.getSession().setAttribute("errorOccurred", true);
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}
