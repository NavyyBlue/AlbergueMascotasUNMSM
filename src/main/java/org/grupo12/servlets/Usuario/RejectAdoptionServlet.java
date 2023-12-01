package org.grupo12.servlets.Usuario;

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

@WebServlet("/usuario/rejectAdoption")
public class RejectAdoptionServlet extends HttpServlet {

    private final HikariDataSource dataSource = ConnectionDB.getDataSource();

    private final AdoptionService adoptionService;

    public RejectAdoptionServlet(){
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
            int userId = Integer.parseInt(request.getParameter("rejectAdoptionUserId"));
            int petId = Integer.parseInt(request.getParameter("rejectAdoptionPetId"));
            int userPetId = Integer.parseInt(request.getParameter("rejectAdoptionUserPetId"));
            boolean success = adoptionService.rejectAdoption(userId, petId, userPetId);
            if(success){
                request.getSession().setAttribute("alerts", Collections.singletonMap("success", "Se ha rechazado la adopción de la mascota"));
            }else{
                request.getSession().setAttribute("alerts", Collections.singletonMap("danger", "No se ha podido rechazar la adopción de la mascota"));
            }
            response.sendRedirect(request.getContextPath() + "/usuario");
        }catch (Exception e){
            request.getSession().setAttribute("errorOccurred", true);
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}
