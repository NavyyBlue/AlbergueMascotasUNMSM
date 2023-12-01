package org.grupo12.servlets.Login;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.UserDAO;
import org.grupo12.models.User;
import org.grupo12.services.UserService;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;
import java.util.Collections;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private final HikariDataSource dataSource = ConnectionDB.getDataSource();
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService(new UserDAO(dataSource));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext()
                .getRequestDispatcher("/WEB-INF/views/Login/signup.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            User user = new User();
            user.setUserName(request.getParameter("userName"));
            user.setPassword(request.getParameter("password"));
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            user.setEmail(request.getParameter("email"));
            user.setPhoneNumber(request.getParameter("phoneNumber"));

            boolean resp = userService.createUser(user);
            if(resp) {
                request.getSession().setAttribute("alerts", Collections.singletonMap("success","Usuario creado correctamente"));
                response.sendRedirect(request.getContextPath() + "/login");
            }else{
                request.getSession().setAttribute("alerts", Collections.singletonMap("danger","Error al crear el usuario"));
                response.sendRedirect("signup");
            }
        }catch (Exception e) {
            request.getSession().setAttribute("errorOccurred", true);
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

}
