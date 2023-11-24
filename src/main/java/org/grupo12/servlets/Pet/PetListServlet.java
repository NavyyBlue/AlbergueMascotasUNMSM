package org.grupo12.servlets.Pet;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.PetDAO;
import org.grupo12.models.Pet;
import org.grupo12.services.PetService;
import org.grupo12.util.ConnectionDB;
import org.grupo12.util.Pagination;

import java.io.IOException;
import java.util.List;

@WebServlet("/petlist")
public class PetListServlet extends HttpServlet {
    private final HikariDataSource dataSource = ConnectionDB.getDataSource();

    private PetService petService;

    @Override
    public void init() throws ServletException {
        petService = new PetService(new PetDAO(dataSource));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            List<Pet> pets = petService.getPetPaginated(request);

            request.setAttribute("pets", pets);
            request.getRequestDispatcher("/WEB-INF/views/pet/petlist.jsp").forward(request, response);
        }catch (Exception e) {
            request.getSession().setAttribute("errorOccurred", true);
            response.sendRedirect(request.getContextPath() + "/error");
        }

    }
}
