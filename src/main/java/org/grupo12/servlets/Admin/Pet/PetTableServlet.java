package org.grupo12.servlets.Admin.Pet;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.PetDAO;
import org.grupo12.models.Pet;
import org.grupo12.services.PetService;
import org.grupo12.util.AuthenticationUtils;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@WebServlet("/admin/petTable")
public class PetTableServlet extends HttpServlet {
    private HikariDataSource dataSource = ConnectionDB.getDataSource();
    private PetService petService;

    @Override
    public void init() throws ServletException {
        petService = new PetService(new PetDAO(dataSource));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            if (!AuthenticationUtils.isAuthenticatedAsAdmin(request, response)) {
                return;
            }

            //Pass the list of pets to the view
            List<Pet> pets = petService.getPetPaginated(request);
            Gson gson = new Gson();
            String petsJson = gson.toJson(pets);


            request.setAttribute("pets", pets);
            request.setAttribute("petsJson", petsJson);

            request.getRequestDispatcher("/WEB-INF/views/admin/pet/petTable.jsp").forward(request, response);

        } catch (Exception e) {
            request.getSession().setAttribute("errorOccurred", true);
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("_method");
        if ("DELETE".equalsIgnoreCase(method)) {
            handleDeleteRequest(request, response);
        } else if ("PATCH".equalsIgnoreCase(method)) {
            handleRestoreRequest(request, response);
        } else {
            String editPetId = request.getParameter("editPetId");
            if (Objects.equals(editPetId, "")){
                handleInsertRequest(request, response);
            }else{
                handleUpdateRequest(request, response);
            }
        }
    }

    private void handleInsertRequest(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String editName = request.getParameter("editName");
        int editAge = Integer.parseInt(request.getParameter("editAge"));
        int editSpeciesId = Integer.parseInt(request.getParameter("editSpeciesId"));
        String editGender = request.getParameter("editGender");
        String editDescription = request.getParameter("editDescription");
        int editAdoptionStatus = Integer.parseInt(request.getParameter("editAdoptionStatus"));
        String editBreed = request.getParameter("editBreed");
        int editLocation = Integer.parseInt(request.getParameter("editLocation"));


        Pet insertPet = new Pet();
        insertPet.setName(editName);
        insertPet.setAge(editAge);
        insertPet.setSpeciesId(editSpeciesId);
        insertPet.setGender(editGender);
        insertPet.setDescription(editDescription);
        insertPet.setAdoptionStatusId(editAdoptionStatus);
        insertPet.setBreed(editBreed);
        insertPet.setLocation(editLocation);

        boolean success = petService.insertPet(insertPet);

        handleOperationResult(success, request, response);
    }

    private void handleDeleteRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int petId = Integer.parseInt(request.getParameter("deletePetId"));
        boolean success = petService.deletePet(petId);

        handleOperationResult(success, request, response);
    }

    private void handleRestoreRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int petId = Integer.parseInt(request.getParameter("restorePetId"));
        boolean success = petService.restorePet(petId);

        handleOperationResult(success, request, response);
    }

    private void handleUpdateRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int editPetId = Integer.parseInt(request.getParameter("editPetId"));
        String editName = request.getParameter("editName");
        int editAge = Integer.parseInt(request.getParameter("editAge"));
        int editSpeciesId = Integer.parseInt(request.getParameter("editSpeciesId"));
        String editGender = request.getParameter("editGender");
        String editDescription = request.getParameter("editDescription");
        int editAdoptionStatus = Integer.parseInt(request.getParameter("editAdoptionStatus"));
        String editBreed = request.getParameter("editBreed");
        int editLocation = Integer.parseInt(request.getParameter("editLocation"));
        String editEntryDate = request.getParameter("editEntryDate");


        Pet updatedPet = new Pet();
        updatedPet.setPetId(editPetId);
        updatedPet.setName(editName);
        updatedPet.setAge(editAge);
        updatedPet.setSpeciesId(editSpeciesId);
        updatedPet.setGender(editGender);
        updatedPet.setDescription(editDescription);
        updatedPet.setAdoptionStatusId(editAdoptionStatus);
        updatedPet.setBreed(editBreed);
        updatedPet.setLocation(editLocation);
        updatedPet.setEntryDate(editEntryDate);

        boolean success = petService.updatePet(updatedPet);

        handleOperationResult(success, request, response);
    }

    private void handleOperationResult(boolean success, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (success) {
            request.getSession().setAttribute("alerts", Collections.singletonMap("success", "La operación se realizó con éxito"));
        } else {
            request.getSession().setAttribute("alerts", Collections.singletonMap("danger", "Hubo un error al procesar la solicitud"));
        }
        response.sendRedirect(request.getContextPath() + "/admin/petTable");
    }

}
