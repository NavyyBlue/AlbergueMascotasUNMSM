package org.grupo12.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.models.User;

import java.io.IOException;

public class AuthenticationUtils {

    private static final int ADMIN_VALUE = 0;
    private static final String HOME_PATH = "/home";

    public static boolean isAuthenticated(HttpServletRequest request) throws IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            redirectToHome(request);
            return false;
        }

        return true;
    }

    public static boolean isAdmin(HttpServletRequest request) throws IOException {
        if (!isAuthenticated(request)) {
            return false;
        }

        int userRole = ((User) request.getSession().getAttribute("user")).getUserRole();

        if (userRole != ADMIN_VALUE) {
            redirectToHome(request);
            return false;
        }

        return true;
    }

    private static void redirectToHome(HttpServletRequest request) throws IOException {
        HttpServletResponse response = (HttpServletResponse) request.getAttribute("response");
        response.sendRedirect(request.getContextPath() + HOME_PATH);
    }
}
