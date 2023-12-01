package org.grupo12.servlets.Admin.Adoption;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.AdoptionDAO;
import org.grupo12.services.implementation.AdoptionService;
import org.grupo12.services.implementation.EmailService;
import org.grupo12.util.ConfigLoader;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;
import java.util.Collections;


@WebServlet("/admin/confirmAdoption")
public class ConfirmAdoptionServlet extends HttpServlet {
    private final HikariDataSource dataSource = ConnectionDB.getDataSource();

    private final AdoptionService adoptionService;

    public ConfirmAdoptionServlet(){
        String smtpHost = ConfigLoader.getProperty("email.smtp.host");
        String smtpPort = ConfigLoader.getProperty("email.smtp.port");
        String smtpUsername = ConfigLoader.getProperty("email.username");
        String smtpPassword = ConfigLoader.getProperty("email.password");
        this.adoptionService = new AdoptionService(new EmailService(smtpHost, smtpPort, smtpUsername, smtpPassword),
                                                    new AdoptionDAO(dataSource));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            int userId = Integer.parseInt(request.getParameter("confirmAdoptionUserId"));
            int petId = Integer.parseInt(request.getParameter("confirmAdoptionPetId"));
            int userPetId = Integer.parseInt(request.getParameter("confirmAdoptionUserPetId"));
            boolean success = adoptionService.completeAdoption(userId, petId, userPetId);
            if(success){
                request.getSession().setAttribute("alerts", Collections.singletonMap("success", "Se ha registrado la adopción de la mascota"));
            }else{
                request.getSession().setAttribute("alerts", Collections.singletonMap("danger", "No se ha podido registrar la adopción de la mascota"));
            }
            response.sendRedirect(request.getContextPath() + "/admin/adoptionsTable");
        }catch (Exception e){
            request.getSession().setAttribute("errorOccurred", true);
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}
