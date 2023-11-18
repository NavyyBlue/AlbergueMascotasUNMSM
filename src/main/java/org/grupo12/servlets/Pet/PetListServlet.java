package org.grupo12.servlets.Pet;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.PetDAO;
import org.grupo12.models.Pet;
import org.grupo12.util.ConnectionDB;
import org.grupo12.util.Pagination;

import java.io.IOException;
import java.util.List;

@WebServlet("/petlist")
public class PetListServlet extends HttpServlet {
    private HikariDataSource dataSource = ConnectionDB.getDataSource();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int speciesId = 0;
        String speciesIdParam = request.getParameter("speciesId");
        if (speciesIdParam != null && !speciesIdParam.isEmpty())
            speciesId = Integer.parseInt(speciesIdParam);

        Pagination pagination = new Pagination();
        PetDAO petDAO = new PetDAO(dataSource);

        // Retrieve the requested page number from the request
        String pageParam = request.getParameter("page");
        int requestedPage = 1; // Default to page 1
        if (pageParam != null && !pageParam.isEmpty()) {
            requestedPage = Integer.parseInt(pageParam);
        }

        int total = petDAO.getTotalPetCount(speciesId);
        pagination.setTotal(total);
        pagination.calculate();
        pagination.setCurrentPage(requestedPage);

        int offset = pagination.calculateOffset(requestedPage);
        int limit = pagination.getLimit();


        List<Pet> pets = petDAO.getPetListBySpecies(speciesId, offset, limit);

        request.setAttribute("pagination", pagination);
        request.setAttribute("pets", pets);
        request.getRequestDispatcher("/WEB-INF/views/pet/petlist.jsp").forward(request, response);
    }
}