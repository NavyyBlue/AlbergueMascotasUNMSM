package org.grupo12.servlets.Usuario;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.grupo12.dao.UserDAO;
import org.grupo12.models.User;
import org.grupo12.services.UserService;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;
import java.util.Collections;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {

    private final HikariDataSource dataSource = ConnectionDB.getDataSource();
    private UserService userService;
    @Override
    public void init() throws ServletException {
        userService = new UserService(new UserDAO(dataSource));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/usuario/usuario.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleUpdateRequest(request, response);
    }

    private void handleUpdateRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int editUserId = Integer.parseInt(request.getParameter("editUserId"));
        String editFirstName = request.getParameter("editFirstName");
        String editLastName = request.getParameter("editLastName");
        String editEmail = request.getParameter("editEmail");
        String editUserName = request.getParameter("editUserName");
        String editPhoneNumber = request.getParameter("editPhoneNumber");

        // Objeto User con los nuevos datos
        User updatedUser = new User();
        updatedUser.setUserId(editUserId);
        updatedUser.setFirstName(editFirstName);
        updatedUser.setLastName(editLastName);
        updatedUser.setEmail(editEmail);
        updatedUser.setUserName(editUserName);
        updatedUser.setPhoneNumber(editPhoneNumber);

        boolean success = userService.updateUser(updatedUser);

        handleOperationResult(success, request, response);
        if (success) {
            // Actualiza el objeto User en la sesión con los nuevos datos
            HttpSession session = request.getSession();
            User updatedUserInSession = (User) session.getAttribute("user");
            updatedUserInSession.setFirstName(updatedUser.getFirstName());
            updatedUserInSession.setLastName(updatedUser.getLastName());
            updatedUserInSession.setEmail(updatedUser.getEmail());
            updatedUserInSession.setUserName(updatedUser.getUserName());
            updatedUserInSession.setPhoneNumber(updatedUser.getPhoneNumber());

            // Vuelve a establecer el objeto User actualizado en la sesión
            session.setAttribute("user", updatedUserInSession);
        }
    }

    private void handleOperationResult(boolean success, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (success) {
            request.getSession().setAttribute("alerts", Collections.singletonMap("success", "La operación se realizó con éxito"));
        } else {
            request.getSession().setAttribute("alerts", Collections.singletonMap("danger", "Hubo un error al procesar la solicitud"));
        }
        response.sendRedirect(request.getRequestURI());
    }

}
