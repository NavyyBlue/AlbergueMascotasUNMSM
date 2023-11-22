package org.grupo12.services;

import jakarta.servlet.http.HttpServletRequest;
import org.grupo12.dao.UserDAO;
import org.grupo12.models.User;
import org.grupo12.util.Pagination;

import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getUsersPaginated(HttpServletRequest request) {
        int userId = 0;
        int active = 1;

        String userIdParam = request.getParameter("userId");
        if (userIdParam != null && !userIdParam.isEmpty())
            userId = Integer.parseInt(userIdParam);

        String activeParam = request.getParameter("active");
        if (activeParam != null && !activeParam.isEmpty())
            active = Integer.parseInt(activeParam);

        Pagination pagination = new Pagination();

        // Retrieve the requested page number from the request
        String pageParam = request.getParameter("page");
        int requestedPage = 1; // Default to page 1
        if (pageParam != null && !pageParam.isEmpty()) {
            requestedPage = Integer.parseInt(pageParam);
        }

        int total = userDAO.getTotalCountUsers(active);
        pagination.setTotal(total);
        pagination.calculate();
        pagination.setCurrentPage(requestedPage);

        int offset = pagination.calculateOffset(requestedPage);
        int limit = pagination.getLimit();

        request.setAttribute("pagination", pagination);

        return userDAO.getUsers(userId, active, offset, limit);
    }

    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    public boolean restoreUser(int userId) {
        return userDAO.restoreUser(userId);
    }

    public boolean updateUser(User updatedUser) {
        return userDAO.updateUser(updatedUser);
    }
}
