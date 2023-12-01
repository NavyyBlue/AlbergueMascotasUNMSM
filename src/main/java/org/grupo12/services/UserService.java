package org.grupo12.services;

import jakarta.servlet.http.HttpServletRequest;
import org.grupo12.dao.UserDAO;
import org.grupo12.models.User;
import org.grupo12.util.Pagination;
import org.grupo12.util.PasswordEncryptionUtil;

import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        String hashedPassword = userDAO.getHashedPasswordByUsername(username);
        if(hashedPassword == null)
            return null;
        boolean isPasswordCorrect = PasswordEncryptionUtil.checkPassword(password, hashedPassword);

        if(!isPasswordCorrect)
            return null;
        return userDAO.getUserByUsernameAndPassword(username, hashedPassword);
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

    public boolean createUser(User newUser) {
        String plainPassword = newUser.getPassword();
        String encryptedPassword = PasswordEncryptionUtil.encryptPassword(plainPassword);
        newUser.setPassword(encryptedPassword);

        return userDAO.createUser(newUser);
    }

    public boolean updateUserPassword(int userId, String newPassword) {
        String encryptedPassword = PasswordEncryptionUtil.encryptPassword(newPassword);
        return userDAO.updateUserPassword(userId, encryptedPassword);
    }

    public boolean verifyUserExistenceByEmail(String email){
        return userDAO.verifyUserExistenceByEmail(email);
    }
}
