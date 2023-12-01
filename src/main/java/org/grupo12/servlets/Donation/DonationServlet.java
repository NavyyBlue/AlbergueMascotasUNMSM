package org.grupo12.servlets.Donation;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.DonationDAO;
import org.grupo12.services.implementation.DonationService;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;

@WebServlet("/donation")
public class DonationServlet extends HttpServlet {

    private final HikariDataSource dataSource = ConnectionDB.getDataSource();

    private DonationService donationService;

    @Override
    public void init() throws ServletException {
        donationService = new DonationService(new DonationDAO(dataSource));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/donation/donation.jsp")
                .forward(request, response);
    }

}
