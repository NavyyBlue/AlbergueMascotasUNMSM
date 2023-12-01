package org.grupo12.servlets.Admin.Donation;


import com.google.gson.Gson;
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
import org.grupo12.util.AuthenticationUtils;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/admin/donationstable")
public class DonationsTableServlet extends HttpServlet {
    private final HikariDataSource dataSource = ConnectionDB.getDataSource();
    private DonationService donationService;
    @Override
    public void init() throws ServletException {
        donationService = new DonationService(new DonationDAO(dataSource));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            // Verificar la autorización de administrador
            if (!AuthenticationUtils.isAuthenticatedAsAdmin(request, response)) {
                return;
            }

            List<Donation> donations = donationService.getDonationsPaginated(request);

            Gson gson = new Gson();
            String donationsJson = gson.toJson(donations);
            request.setAttribute("donations", donations);
            request.setAttribute("donationsJson", donationsJson);
            request.getRequestDispatcher("/WEB-INF/views/admin/donation/donationtable.jsp").forward(request, response);
        }catch (Exception e) {
            request.getSession().setAttribute("errorOccurred", true);
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String method = request.getParameter("_method");
        if ("DELETE".equalsIgnoreCase(method)) {
            handleDeleteRequest(request, response);
        } else if ("PATCH".equalsIgnoreCase(method)) {
            handleRestoreRequest(request, response);
        }else{
            handleUpdateRequest(request, response);
        }
    }

    private void handleDeleteRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int donationId = Integer.parseInt(request.getParameter("deleteDonationId"));
        boolean success = donationService.deleteDonation(donationId);

        handleOperationResult(success, request, response);
    }

    private void handleRestoreRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int donationId = Integer.parseInt(request.getParameter("restoreDonationId"));
        boolean success = donationService.restoreDonation(donationId);

        handleOperationResult(success, request, response);
    }

    private void handleUpdateRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int donationId = Integer.parseInt(request.getParameter("editDonationId"));
        String fullNameDonator = request.getParameter("fullNameDonator");
        String phoneNumberDonator = request.getParameter("phoneNumberDonator");
        float amount = Float.parseFloat(request.getParameter("amount"));
        int methodPaymentId = Integer.parseInt(request.getParameter("methodPaymentId"));
        String paymentDate = request.getParameter("paymentDate");

        //Send null paymendDate if empty
        if(paymentDate.isEmpty()){
            paymentDate = null;
        }

        Donation donation = new Donation();
        donation.setDonationId(donationId);
        donation.setFullNameDonator(fullNameDonator);
        donation.setPhoneNumberDonator(phoneNumberDonator);
        donation.setAmount(amount);
        donation.setMethodPaymentId(methodPaymentId);
        donation.setPaymentDate(paymentDate);

        System.out.println("donation: "+donation);

        boolean success = donationService.updateDonation(donation);

        handleOperationResult(success, request, response);
    }

    private void handleOperationResult(boolean success, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (success) {
            request.getSession().setAttribute("alerts", Collections.singletonMap("success", "La operación se realizó con éxito"));
        } else {
            request.getSession().setAttribute("alerts", Collections.singletonMap("danger", "Hubo un error al procesar la solicitud"));
        }
        response.sendRedirect(request.getRequestURI());
    }

}
