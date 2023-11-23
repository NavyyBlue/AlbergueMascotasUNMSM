package org.grupo12.servlets.Login;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import org.grupo12.services.implementation.EmailService;
import org.grupo12.services.implementation.PasswordRecoveryService;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    private final PasswordRecoveryService recoveryService;

    public ForgotPasswordServlet() {
        this.recoveryService = new PasswordRecoveryService(new EmailService("smtp.gmail.com", "587", "", ""), null);
    }
}
