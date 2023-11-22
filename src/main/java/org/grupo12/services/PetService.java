package org.grupo12.services;

import jakarta.servlet.http.HttpServletRequest;
import org.grupo12.dao.PetDAO;
import org.grupo12.models.Pet;
import org.grupo12.util.Pagination;

import java.util.List;

public class PetService {
    private final PetDAO petDAO;

    public PetService(PetDAO petDAO) {
        this.petDAO = petDAO;
    }

    public List<Pet> getPetPaginated(HttpServletRequest request){
        int speciesId = 0;
        int age = 0;
        String gender = "";
        String searchKeyword = "";

        String speciesIdParam = request.getParameter("speciesId");
        if (speciesIdParam != null && !speciesIdParam.isEmpty())
            speciesId = Integer.parseInt(speciesIdParam);

        String ageParam = request.getParameter("age");
        if (ageParam != null && !ageParam.isEmpty()) {
            age = Integer.parseInt(ageParam);
        }

        gender = request.getParameter("gender");
        searchKeyword = request.getParameter("searchKeyword");

        Pagination pagination = new Pagination();

        // Retrieve the requested page number from the request
        String pageParam = request.getParameter("page");
        int requestedPage = 1; // Default to page 1
        if (pageParam != null && !pageParam.isEmpty()) {
            requestedPage = Integer.parseInt(pageParam);
        }

        int total = petDAO.getTotalPetCount(speciesId, age, gender, searchKeyword);
        pagination.setTotal(total);
        pagination.calculate();
        pagination.setCurrentPage(requestedPage);

        int offset = pagination.calculateOffset(requestedPage);
        int limit = pagination.getLimit();

        request.setAttribute("pagination", pagination);

        return petDAO.getPetListBySpecies(speciesId, age, gender, searchKeyword,offset, limit);
    }
}
