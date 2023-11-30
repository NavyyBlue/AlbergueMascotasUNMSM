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
            if(mainImage != null ) request.setAttribute("mainImage", mainImage);
            if(images != null && !images.isEmpty()) request.setAttribute("images", images);

            request.getRequestDispatcher("/WEB-INF/views/admin/pet/petImage.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/admin/petTable");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Verificar si la solicitud es de tipo 'multipart'
        if (JakartaServletFileUpload.isMultipartContent(request)) {
            try {
                // Crear una instancia de JakartaServletDiskFileUpload
                JakartaServletDiskFileUpload upload = new JakartaServletDiskFileUpload();

                // Obtener la lista de elementos del formulario
                List<DiskFileItem> formItems = upload.parseRequest(request);

                // Variables para almacenar el ID y el archivo
                List<Image> image = new ArrayList<>();

                boolean isMainImage = false;
                int petId = -1;
                String relativePath = null;
                String filePath = null;

                // Iterar sobre los elementos del formulario
                for (DiskFileItem item : formItems) {
                    if (item.isFormField()) {
                        // Si es un campo de formulario normal
                        if ("uploadPetId".equals(item.getFieldName())) {
                            petId = Integer.parseInt(item.getString());
                        }
                    } else {
                        isMainImage = "uploadImage".equals(item.getFieldName());
                        String fileName = new File(item.getName()).getName();
                        relativePath = File.separator + "assets" + File.separator + "uploads" + File.separator + "petImages" + File.separator + fileName;
                        filePath = getServletContext().getRealPath("") + relativePath;
                        File storeFile = new File(filePath);
                        item.write(storeFile.toPath());


                        //Set data to image
                        Image data = new Image();
                        data.setPetId(petId);
                        data.setImageUrl(relativePath);
                        data.setMainImage(isMainImage);

                        image.add(data);
                    }
                }

                for(Image img : image){
                    petImageService.uploadPetImage(img.getPetId(), img.getImageUrl(), img.isMainImage());
                }

                request.getSession().setAttribute("alerts", Collections.singletonMap("success", "Imagen subida correctamente"));
            } catch (Exception e) {
                request.getSession().setAttribute("alerts", Collections.singletonMap("danger", "Ocurrió un error al subir la imagen"));
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/petImage?petId=" + request.getParameter("uploadPetId"));
    }
}
