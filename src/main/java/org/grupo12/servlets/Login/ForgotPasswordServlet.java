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
            boolean resp = recoveryService.sendOTPByEmail(email);
            if(resp){
                request.getSession().setAttribute("alerts", Collections.singletonMap("success", "Email enviado correctamente"));
                request.getSession().setAttribute("userEmail", email);
                response.sendRedirect(request.getContextPath() + "/verifyotp");
            }else{
                request.getSession().setAttribute("alerts", Collections.singletonMap("danger", "Verifique el email ingresado"));
                response.sendRedirect(request.getRequestURI());
            }

        }catch (Exception e) {
            request.getSession().setAttribute("alerts", Collections.singletonMap("danger", "Error al enviar el email"));
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

}
