package org.grupo12.servlets.Pet;


import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.PetFavoriteDAO;
import org.grupo12.models.User;
import org.grupo12.services.implementation.PetFavoriteService;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;
import java.util.List;

@WebServlet("/petfavorite")
public class PetFavoriteServlet extends HttpServlet {
    private final HikariDataSource dataSource = ConnectionDB.getDataSource();

    private final PetFavoriteService petFavoriteService;

    public PetFavoriteServlet() {
        this.petFavoriteService = new PetFavoriteService(new PetFavoriteDAO(dataSource));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            User user = (User) request.getSession().getAttribute("user");
            int userId = user.getUserId();
            int petId = Integer.parseInt(request.getParameter("petId"));

            List<Integer> isFavorite = petFavoriteService.getFavoritePetsByUser(userId);

            if(isFavorite.contains(petId)){
                response.getWriter().write("{\"success\": true, \"isFavorite\": true}");
            }else{
                response.getWriter().write("{\"success\": true, \"isFavorite\": false}");
            }

        }catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            User user = (User) request.getSession().getAttribute("user");
            int userId = user.getUserId();
            int petId = Integer.parseInt(request.getParameter("petId"));

            int isFavorite = petFavoriteService.isFavorite(userId, petId);
            //total de favoritos por mascota
            int totalFavorites;
            boolean resp;

            //Remover de favoritos
            if(isFavorite == 1){
                resp = petFavoriteService.updateFavorite(userId, petId, 0);
                if(!resp){
                    response.getWriter().write("{\"success\": false}");
                    return;
                }
                totalFavorites = petFavoriteService.getTotalFavoritesByPet(petId);
                response.getWriter().write("{\"success\": true, \"isFavorite\": false, \"favoriteCount\": " + totalFavorites + "}");
            }
            //Actualizar favoritos
            else if(isFavorite == 0){
                resp = petFavoriteService.updateFavorite(userId, petId, 1);
                if(!resp){
                    response.getWriter().write("{\"success\": false}");
                    return;
                }
                totalFavorites = petFavoriteService.getTotalFavoritesByPet(petId);
                response.getWriter().write("{\"success\": true, \"isFavorite\": true, \"favoriteCount\": " + totalFavorites + "}");

            }
            //Agregar a favoritos
            else{
                resp = petFavoriteService.addFavorite(userId, petId);
                if(!resp){
                    response.getWriter().write("{\"success\": false}");
                    return;
                }
                totalFavorites = petFavoriteService.getTotalFavoritesByPet(petId);
                response.getWriter().write("{\"success\": true, \"isFavorite\": true, \"favoriteCount\": " + totalFavorites + "}");

            }

        }catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
