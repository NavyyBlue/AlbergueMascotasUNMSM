package org.grupo12.servlets.PetList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.PetDAO;
import org.grupo12.models.Pet;
import org.grupo12.util.Pagination;

import java.io.IOException;
import java.util.List;

@WebServlet("/petlist")
public class PetListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int speciesId;
        String speciesIdParam = request.getParameter("speciesId");
        if (speciesIdParam != null && !speciesIdParam.isEmpty()) {
            speciesId = Integer.parseInt(speciesIdParam);
        } else {
            speciesId = 0; // Valor por defecto si no se proporciona speciesId en la solicitud
        }
        Pagination pagination = new Pagination();
        PetDAO petDAO = new PetDAO();

        int offset = pagination.getOffset();
        int limit = pagination.getLimit();

        int total = petDAO.getTotalPetCount(speciesId);
        pagination.setTotal(total);
        pagination.calculate();
        pagination.setCurrentPage(1);

        List<Pet> pets = speciesId < 1 ? petDAO.getPetListBySpecies(offset, limit) : petDAO.getPetListBySpecies(speciesId, offset, limit);

        request.setAttribute("pagination", pagination);
        request.setAttribute("pets", pets);
        request.getRequestDispatcher("/WEB-INF/views/petlist/petlist.jsp").forward(request, response);
    }
}
