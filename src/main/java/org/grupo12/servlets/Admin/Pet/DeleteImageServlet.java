package org.grupo12.servlets.Admin.Pet;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.PetImageDAO;
import org.grupo12.models.Image;
import org.grupo12.services.implementation.ImagePetService;
import org.grupo12.util.ConnectionDB;
import org.grupo12.util.ImageUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

@WebServlet("/admin/deleteImage")
public class DeleteImageServlet extends HttpServlet {
    private HikariDataSource dataSource = ConnectionDB.getDataSource();
    private final ImagePetService petImageService;

    public DeleteImageServlet() {
        this.petImageService = new ImagePetService(new PetImageDAO(dataSource));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int petId = Integer.parseInt(request.getParameter("petId"));
        try {
            int imageId = Integer.parseInt(request.getParameter("imageId"));

            String imageUrl = request.getParameter("imageUrl");

            if(imageUrl.equals(ImageUtil.defaultPath)){
                petImageService.deletePetImage(imageId);
                request.getSession().setAttribute("alerts", Collections.singletonMap("success", "Imagen eliminada correctamente"));
                return;
            }

            String filePath = request.getServletContext().getRealPath("") + imageUrl;

            File fileToDelete = new File(filePath);

            if(fileToDelete.exists()){
                if(fileToDelete.delete()) {
                    petImageService.deletePetImage(imageId);
                }
            }

            request.getSession().setAttribute("alerts", Collections.singletonMap("success", "Imagen eliminada correctamente"));

        } catch (Exception e) {
            request.getSession().setAttribute("alerts", Collections.singletonMap("success", "Error al eliminar la imagen"));
        }finally {
            response.sendRedirect(request.getContextPath() + "/admin/petImage?petId=" + petId);
        }

    }
}
