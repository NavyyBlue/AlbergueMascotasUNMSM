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

@WebServlet("/resetpassword")
public class ResetPasswordServlet extends HttpServlet {
    private final HikariDataSource dataSource = ConnectionDB.getDataSource();
    private final PasswordRecoveryService passwordRecoveryService;

    public ResetPasswordServlet() {
        this.passwordRecoveryService = new PasswordRecoveryService(new ResetPasswordDAO(dataSource));
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/Login/resetpassword.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            request.getSession().setAttribute("errorOccurred", true);
            response.sendRedirect(request.getContextPath() + "/error");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");
            String otp = (String) request.getSession().getAttribute("otp");
            if(password.equals(password2)){
                boolean resp = passwordRecoveryService.resetPassword(otp, password);
                if(resp) response.sendRedirect(request.getContextPath() + "/login");
            }else{
                response.sendRedirect("resetpassword");
            }
        }catch (Exception e) {
            request.getSession().setAttribute("errorOccurred", true);
        }finally {
            request.getSession().removeAttribute("otp");
        }
    }
}
