package org.grupo12.services;

import jakarta.servlet.http.HttpServletRequest;
import org.grupo12.dao.PetDAO;
import org.grupo12.models.Pet;
import org.grupo12.models.User;
import org.grupo12.util.Pagination;

import java.util.List;

public class PetService {
    private final PetDAO petDAO;

    public PetService(PetDAO petDAO) {
        this.petDAO = petDAO;
    }

    public List<Pet> getPetPaginatedBySpecies(HttpServletRequest request){
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

    public List<Pet> getPetPaginated(HttpServletRequest request){
        int petId = 0;
        int speciesId = 0;
        int age = 0;
        int active = 2;
        String gender = "";
        String searchKeyword = "";

        String userIdParam = request.getParameter("userId");
        if (userIdParam != null && !userIdParam.isEmpty())
            petId = Integer.parseInt(userIdParam);

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

        return petDAO.getPets(petId,active, offset, limit);
    }

    public boolean deletePet(int petId) {return petDAO.deletePet(petId);}
    public boolean restorePet(int petId) {return petDAO.restorePet(petId);}
    public boolean updatePet(Pet updatedPet) {
        return petDAO.updatePet(updatedPet);
    }

    public boolean insertPet(Pet insertPet){return  petDAO.insertPet(insertPet);}
}
