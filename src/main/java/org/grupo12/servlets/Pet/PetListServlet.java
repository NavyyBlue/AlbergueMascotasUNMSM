package org.grupo12.servlets.Pet;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.Session;
import org.grupo12.dao.PetDAO;
import org.grupo12.dao.PetFavoriteDAO;
import org.grupo12.models.Pet;
import org.grupo12.models.User;
import org.grupo12.services.PetService;
import org.grupo12.services.implementation.PetFavoriteService;
import org.grupo12.util.ConnectionDB;
import org.grupo12.util.Pagination;
import org.grupo12.websockets.notifiers.PetFavoriteNotifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/petlist")
public class PetListServlet extends HttpServlet {
    private final HikariDataSource dataSource = ConnectionDB.getDataSource();

    private PetService petService;
    private final PetFavoriteService petFavoriteService = new PetFavoriteService(new PetFavoriteDAO(dataSource));

    @Override
    public void init() throws ServletException {
        petService = new PetService(new PetDAO(dataSource));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            List<Pet> pets = petService.getPetPaginatedBySpecies(request);
            List<Pet> petsWithFavorites = new ArrayList<>();

            //Obtener favoritos
            User user = (User) request.getSession().getAttribute("user");
            if(user != null){
                int userId = user.getUserId();
                List<Integer> favoritePets = petFavoriteService.getFavoritePetsByUser(userId);
                request.setAttribute("favoritePets", favoritePets);

                //Obtener total de favoritos por mascota
                for (Pet pet : pets) {
                    int totalFavorites = petFavoriteService.getTotalFavoritesByPet(pet.getPetId());
                    pet.setTotalFavorites(totalFavorites);
                    petsWithFavorites.add(pet);
                }

                System.out.println("petsWithFavorites: " + petsWithFavorites);

                handleFavoriteChange(petsWithFavorites, user);
            }

            request.setAttribute("pets", pets);
            request.getRequestDispatcher("/WEB-INF/views/pet/petlist.jsp").forward(request, response);
        }catch (Exception e) {
            request.getSession().setAttribute("errorOccurred", true);
            response.sendRedirect(request.getContextPath() + "/error");
        }

    }

    private void handleFavoriteChange(List<Pet> pets, User user) {
        for (Pet pet : pets) {
            int petId = pet.getPetId();
            int oldFavoriteCount = pet.getTotalFavorites();

            PetFavoriteNotifier.notifyFavoritesChanged(petId, oldFavoriteCount, user.getSession());
            int newFavoriteCount = petFavoriteService.getTotalFavoritesByPet(petId);
            pet.setTotalFavorites(newFavoriteCount);
        }
    }
}
