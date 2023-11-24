package org.grupo12.servlets.Login;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.ResetPasswordDAO;
import org.grupo12.services.implementation.PasswordRecoveryService;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;

@WebServlet("/verifyotp")
public class VerifyOTPServlet extends HttpServlet {
    private final HikariDataSource dataSource = ConnectionDB.getDataSource();
    private final PasswordRecoveryService recoveryService;

    public VerifyOTPServlet() {
        this.recoveryService = new PasswordRecoveryService(new ResetPasswordDAO(dataSource));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/Login/verifyotp.jsp")
                    .forward(request, response);
        }catch (Exception e) {
            request.getSession().setAttribute("errorOccurred", true);
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            String otp = request.getParameter("otpCode");
            boolean resp = recoveryService.verifyOtp(otp);
            if(resp) {
                response.sendRedirect(request.getContextPath() + "/resetpassword");
                request.getSession().setAttribute("otp", otp);
                request.getSession().removeAttribute("userEmail");
            }
        }catch (Exception e) {
            request.getSession().setAttribute("errorOccurred", true);
        }
    }
}
