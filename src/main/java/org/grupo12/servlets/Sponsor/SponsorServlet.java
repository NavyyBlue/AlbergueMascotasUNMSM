package org.grupo12.servlets.Sponsor;

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

@WebServlet("/sponsor")
public class SponsorServlet extends HttpServlet {

    private final HikariDataSource dataSource = ConnectionDB.getDataSource();

    private final AdoptionService adoptionService;
    public SponsorServlet(){
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
            int userId = Integer.parseInt(request.getParameter("SponsorUserId"));
            int petId = Integer.parseInt(request.getParameter("SponsorPetId"));
            int amount = Integer.parseInt(request.getParameter("inlineRadioOptions"));
            int methodPayment = Integer.parseInt(request.getParameter("sponsorMethodPayment"));
            System.out.println(userId);
            System.out.println(amount);
            System.out.println(methodPayment);
            boolean success = adoptionService.requestSponsor(userId, petId, amount, methodPayment);
            if(success){
                request.getSession().setAttribute("alerts", Collections.singletonMap("success", "Se ha registrado el apadrinamiento a la mascota"));
            }else{
                request.getSession().setAttribute("alerts", Collections.singletonMap("danger", "No se ha podido registrar apadrinamiento a la mascota"));
            }
            response.sendRedirect(request.getContextPath() + "/petinfo?petId=" + petId);
        }catch (Exception e){
            request.getSession().setAttribute("errorOccurred", true);
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}
