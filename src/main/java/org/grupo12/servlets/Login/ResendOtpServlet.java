package org.grupo12.servlets.Login;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.ResetPasswordDAO;
import org.grupo12.services.implementation.EmailService;
import org.grupo12.services.implementation.PasswordRecoveryService;
import org.grupo12.util.ConfigLoader;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;
import java.util.Collections;

@WebServlet("/resendotp")
public class ResendOtpServlet extends HttpServlet {
    private final HikariDataSource dataSource = ConnectionDB.getDataSource();
    private final PasswordRecoveryService recoveryService;

    public ResendOtpServlet() {
        String smtpHost = ConfigLoader.getProperty("email.smtp.host");
        String smtpPort = ConfigLoader.getProperty("email.smtp.port");
        String smtpUsername = ConfigLoader.getProperty("email.username");
        String smtpPassword = ConfigLoader.getProperty("email.password");
        this.recoveryService = new PasswordRecoveryService(new EmailService(smtpHost, smtpPort, smtpUsername, smtpPassword),
                new ResetPasswordDAO(dataSource));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            String email = (String) request.getSession().getAttribute("userEmail");
            boolean resp = recoveryService.sendOTPByEmail(email);
            if(!resp){
                request.getSession().setAttribute("alerts", Collections.singletonMap("danger", "Error al enviar el email"));
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            request.getSession().setAttribute("alerts", Collections.singletonMap("success", "Código reenviado correctamente"));
        }catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }finally {
            response.sendRedirect(request.getContextPath() + "/verifyotp");
        }
    }
}
