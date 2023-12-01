package org.grupo12.servlets.Admin.Pet;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload2.core.DiskFileItem;
import org.apache.commons.fileupload2.jakarta.JakartaServletDiskFileUpload;
import org.apache.commons.fileupload2.jakarta.JakartaServletFileUpload;
import org.grupo12.dao.PetImageDAO;
import org.grupo12.models.Image;
import org.grupo12.services.implementation.ImagePetService;
import org.grupo12.util.AuthenticationUtils;
import org.grupo12.util.ConnectionDB;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/admin/petImage")
public class PetImageServlet extends HttpServlet {
    private HikariDataSource dataSource = ConnectionDB.getDataSource();
    private final ImagePetService petImageService;

    public PetImageServlet() {
        this.petImageService = new ImagePetService(new PetImageDAO(dataSource));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (!AuthenticationUtils.isAuthenticatedAsAdmin(request, response)) {
                return;
            }
            if (!request.getParameterMap().containsKey("petId")) {
                response.sendRedirect(request.getContextPath() + "/admin/petTable");
                return;
            }

            int petId = Integer.parseInt(request.getParameter("petId"));
            Image mainImage = petImageService.getMainPetImage(petId);
            List<Image> images = petImageService.getPetImages(petId);

            request.setAttribute("petId", petId);
            request.setAttribute("mainImage", mainImage);
            request.setAttribute("images", images);

            request.getRequestDispatcher("/WEB-INF/views/admin/pet/petImage.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/admin/petTable");
        }
    }
}
