package org.grupo12.servlets.FormDonation;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.DonationDAO;
import org.grupo12.models.Donation;
import org.grupo12.services.implementation.DonationService;
import org.grupo12.services.implementation.EmailService;
import org.grupo12.util.ConfigLoader;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;
import java.util.Collections;

@WebServlet("/FormDonation")

public class FormDonationServlet extends HttpServlet {
    private final HikariDataSource dataSource = ConnectionDB.getDataSource();

    private final DonationService donationService;

    public FormDonationServlet(){
        String smtpHost = ConfigLoader.getProperty("email.smtp.host");
        String smtpPort = ConfigLoader.getProperty("email.smtp.port");
        String smtpUsername = ConfigLoader.getProperty("email.username");
        String smtpPassword = ConfigLoader.getProperty("email.password");
        this.donationService = new DonationService(new EmailService(smtpHost, smtpPort, smtpUsername, smtpPassword),
                                                    new DonationDAO(dataSource));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/form_donation/form_donation.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try{
            String fullNameDonator = request.getParameter("fullname");
            String phoneNumberDonator = request.getParameter("phone");
            float amount = Float.parseFloat(request.getParameter("amount"));
            int methodPaymentId = Integer.parseInt(request.getParameter("paymentMethod"));

            if(phoneNumberDonator.length() != 9){
                request.getSession().setAttribute("alerts", Collections.singletonMap("warning", "Ingrese un número de teléfono válido para Perú"));
                response.sendRedirect(request.getRequestURI());
                return;
            }

            Donation donation = new Donation();
            donation.setFullNameDonator(fullNameDonator);
            donation.setPhoneNumberDonator(phoneNumberDonator);
            donation.setAmount(amount);
            donation.setMethodPaymentId(methodPaymentId);

            boolean resp = donationService.addDonation(donation);

            if(resp){
                request.getSession().setAttribute("alerts", Collections.singletonMap("success", "Donación enviada correctamente, nos comunicaremos con usted a la brevedad"));
                response.sendRedirect(request.getRequestURI());
            }else{
                request.getSession().setAttribute("alerts", Collections.singletonMap("danger", "Verifique los datos ingresados"));
                response.sendRedirect(request.getRequestURI());
            }


        }catch (Exception e) {
            request.getSession().setAttribute("alerts", Collections.singletonMap("danger", "Error al enviar donación"));
            response.sendRedirect(request.getRequestURI());
        }
    }
}
