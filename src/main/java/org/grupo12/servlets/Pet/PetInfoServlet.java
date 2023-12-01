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

import java.io.IOException;
import java.util.List;

@WebServlet("/petinfo")
public class PetInfoServlet extends HttpServlet {
    private final HikariDataSource dataSource = ConnectionDB.getDataSource();
    private PetDAO petDAO;

    @Override
    public void init() throws ServletException {
        petDAO = new PetDAO(dataSource);
    }

    @Override
    protected  void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int petId = Integer.parseInt(request.getParameter("petId"));
        Pet petInfo = petDAO.getPetInfo(petId);
        List<Pet> petImages = petDAO.getPetImages(petId);
        List<Pet> petStatus = petDAO.getPetStatus(petId);

        request.setAttribute("petInfo", petInfo);
        request.setAttribute("petImages", petImages);
        request.setAttribute("petStatus", petStatus);

        request.getRequestDispatcher("/WEB-INF/views/pet/petinfo.jsp").forward(request, response);
    }

}
