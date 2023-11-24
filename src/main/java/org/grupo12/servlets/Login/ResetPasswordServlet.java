package org.grupo12.servlets.Login;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.UserDAO;
import org.grupo12.services.UserService;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;

@WebServlet("/resetpassword")
public class ResetPasswordServlet extends HttpServlet {
    private final HikariDataSource dataSource = ConnectionDB.getDataSource();
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService(new UserDAO(dataSource));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/Login/resetpassword.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");
            if(password.equals(password2)){
                boolean resp = userService.updateUserPassword(2, password);
                if(resp) response.sendRedirect(request.getContextPath() + "/login");
            }else{
                response.sendRedirect("resetpassword");
            }
        }catch (Exception e) {
            request.getSession().setAttribute("errorOccurred", true);
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}
