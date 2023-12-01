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
import org.grupo12.util.ConnectionDB;
import org.grupo12.util.ImageUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/admin/sendMultipleImage")
public class SendMultipleImageServlet extends HttpServlet {
    private HikariDataSource dataSource = ConnectionDB.getDataSource();
    private final ImagePetService petImageService;

    public SendMultipleImageServlet() {
        this.petImageService = new ImagePetService(new PetImageDAO(dataSource));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int petIdAux = 0;
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
                        if ("uploadPetId2".equals(item.getFieldName())) {
                            petId = Integer.parseInt(item.getString());
                            petIdAux = petId;
                        }
                    } else {
                        String fileName = new File(item.getName()).getName();
                        if(fileName.isEmpty()) continue;

                        String uniqueName = ImageUtil.generateUniqueImageName(fileName);

                        relativePath = "/assets/uploads/petImages/" + uniqueName;
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
                    if(img != null){
                        petImageService.uploadPetImage(img.getPetId(), img.getImageUrl(), img.isMainImage());
                    }
                }

                request.getSession().setAttribute("alerts", Collections.singletonMap("success", "Imagen subida correctamente"));
            } catch (Exception e) {
                request.getSession().setAttribute("alerts", Collections.singletonMap("danger", "Ocurrió un error al subir la imagen"));
            } finally {
                response.sendRedirect(request.getContextPath() + "/admin/petImage?petId=" + petIdAux);
            }
        }
    }
}
