package org.grupo12.servlets.Admin.User;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.UserDAO;
import org.grupo12.models.User;
import org.grupo12.services.UserService;
import org.grupo12.util.AuthenticationUtils;
import org.grupo12.util.ConnectionDB;
import org.grupo12.util.Pagination;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/admin/userstable")
public class UsersTableServlet extends HttpServlet {
    private final HikariDataSource dataSource = ConnectionDB.getDataSource();
    private UserService userService;
    @Override
    public void init() throws ServletException {
        userService = new UserService(new UserDAO(dataSource));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            // Verificar la autorización de administrador
            if (!AuthenticationUtils.isAuthenticatedAsAdmin(request, response)) {
                return;
            }

            List<User> users = userService.getUsersPaginated(request);

            // Convert the list of users to JSON using Gson
            Gson gson = new Gson();
            String usersJson = gson.toJson(users);

            request.setAttribute("users", users);
            request.setAttribute("usersJson", usersJson);
            request.getRequestDispatcher("/WEB-INF/views/admin/user/userstable.jsp").forward(request, response);
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
        } else {
            handleUpdateRequest(request, response);
        }
    }

    private void handleDeleteRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("deleteUserId"));
        boolean success = userService.deleteUser(userId);

        handleOperationResult(success, request, response);
    }

    private void handleRestoreRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("restoreUserId"));
        boolean success = userService.restoreUser(userId);

        handleOperationResult(success, request, response);
    }

    private void handleUpdateRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int editUserId = Integer.parseInt(request.getParameter("editUserId"));
        String editFirstName = request.getParameter("editFirstName");
        String editLastName = request.getParameter("editLastName");
        String editEmail = request.getParameter("editEmail");
        String editUserName = request.getParameter("editUserName");
        String editPhoneNumber = request.getParameter("editPhoneNumber");
        int editUserRole = Integer.parseInt(request.getParameter("editUserRole"));

        // Objeto User con los nuevos datos
        User updatedUser = new User();
        updatedUser.setUserId(editUserId);
        updatedUser.setFirstName(editFirstName);
        updatedUser.setLastName(editLastName);
        updatedUser.setEmail(editEmail);
        updatedUser.setUserName(editUserName);
        updatedUser.setPhoneNumber(editPhoneNumber);
        updatedUser.setUserRole(editUserRole);

        boolean success = userService.updateUser(updatedUser);

        handleOperationResult(success, request, response);
    }

    private void handleOperationResult(boolean success, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (success) {
            request.getSession().setAttribute("alerts", Collections.singletonMap("success", "La operación se realizó con éxito"));
        } else {
            request.getSession().setAttribute("alerts", Collections.singletonMap("danger", "Hubo un error al procesar la solicitud"));
        }
        response.sendRedirect(request.getContextPath() + "/admin/userstable");
    }
}
