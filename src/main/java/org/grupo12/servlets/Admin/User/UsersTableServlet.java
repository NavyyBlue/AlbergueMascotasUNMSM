package org.grupo12.servlets.Admin.User;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.UserDAO;
import org.grupo12.models.User;
import org.grupo12.util.ConnectionDB;
import org.grupo12.util.Pagination;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/userstable")
public class UsersTableServlet extends HttpServlet {
    private HikariDataSource dataSource = ConnectionDB.getDataSource();
    private UserDAO userDAO;
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = 0;

        String userIdParam = request.getParameter("userId");
        if (userIdParam != null && !userIdParam.isEmpty())
            userId = Integer.parseInt(userIdParam);

        Pagination pagination = new Pagination();

        // Retrieve the requested page number from the request
        String pageParam = request.getParameter("page");
        int requestedPage = 1; // Default to page 1
        if (pageParam != null && !pageParam.isEmpty()) {
            requestedPage = Integer.parseInt(pageParam);
        }

        int total = userDAO.getTotalCountUsers();
        pagination.setTotal(total);
        pagination.calculate();
        pagination.setCurrentPage(requestedPage);

        int offset = pagination.calculateOffset(requestedPage);
        int limit = pagination.getLimit();

        List<User> users = userDAO.getUsers(userId, offset, limit);

        // Convert the list of users to JSON using Gson
        Gson gson = new Gson();
        String usersJson = gson.toJson(users);

        request.setAttribute("pagination", pagination);
        request.setAttribute("users", users);
        request.setAttribute("usersJson", usersJson);
        request.getRequestDispatcher("/WEB-INF/views/admin/user/userstable.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int editUserId = Integer.parseInt(request.getParameter(("editUserId")));
        String editFirstName = request.getParameter("editFirstName");
        String editLastName = request.getParameter("editLastName");
        String editEmail = request.getParameter("editEmail");
        String editUserName = request.getParameter("editUserName");
        String editPhoneNumber = request.getParameter("editPhoneNumber");
        int editUserRole = Integer.parseInt(request.getParameter("editUserRole"));

        // Crea un objeto User con los nuevos datos
        User updatedUser = new User();
        updatedUser.setUserId(editUserId);
        updatedUser.setFirstName(editFirstName);
        updatedUser.setLastName(editLastName);
        updatedUser.setEmail(editEmail);
        updatedUser.setUserName(editUserName);
        updatedUser.setPhoneNumber(editPhoneNumber);
        updatedUser.setUserRole(editUserRole);

        boolean success = userDAO.updateUser(updatedUser);
        System.out.println("success: " + success);
        if(success){
            request.getSession().setAttribute("editSuccess", "Usuario actualizado correctamente");
        }
        else{
            request.getSession().setAttribute("editError", "Error al actualizar el usuario");
        }

        response.sendRedirect(request.getContextPath() + "/admin/userstable");
    }
}
