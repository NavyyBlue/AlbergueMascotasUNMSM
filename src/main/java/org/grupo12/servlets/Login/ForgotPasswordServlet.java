package org.grupo12.servlets.Login;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.grupo12.dao.ResetPasswordDAO;
import org.grupo12.services.implementation.EmailService;
import org.grupo12.services.implementation.PasswordRecoveryService;
import org.grupo12.util.ConfigLoader;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    private final HikariDataSource dataSource = ConnectionDB.getDataSource();
    private final PasswordRecoveryService recoveryService;

    public ForgotPasswordServlet() {
        String smtpHost = ConfigLoader.getProperty("email.smtp.host");
        String smtpPort = ConfigLoader.getProperty("email.smtp.port");
        String smtpUsername = ConfigLoader.getProperty("email.username");
        String smtpPassword = ConfigLoader.getProperty("email.password");
        this.recoveryService = new PasswordRecoveryService(new EmailService(smtpHost, smtpPort, smtpUsername, smtpPassword),
                                                            new ResetPasswordDAO(dataSource));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/Login/forgotpassword.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            String email = request.getParameter("userEmail");
            recoveryService.sendOTPByEmail(email);
            response.sendRedirect(request.getContextPath() + "/verifyotp");
        }catch (Exception e) {
            System.out.println("error otp: " + e.getMessage());
        }finally {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

}
