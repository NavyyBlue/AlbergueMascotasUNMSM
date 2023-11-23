package org.grupo12.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.models.User;

import java.io.IOException;

public class AuthenticationUtils {

    public static final int ADMIN_VALUE = 0;
    private static final String HOME_PATH = "/home";

    public static boolean isAuthenticated(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            redirectToHome(request, response);
            return false;
        }

        return true;
    }

    public static boolean isAuthenticatedAsAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!isAuthenticated(request, response)) {
            return false;
        }

        int userRole = ((User) request.getSession().getAttribute("user")).getUserRole();

        if (userRole != ADMIN_VALUE) {
            redirectToHome(request, response);
            return false;
        }

        return true;
    }

    private static void redirectToHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + HOME_PATH);
    }
}
