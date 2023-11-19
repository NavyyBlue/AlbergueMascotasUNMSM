package org.grupo12.servlets.Admin;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.PetDAO;
import org.grupo12.models.Pet;
import org.grupo12.util.ConnectionDB;
import org.grupo12.util.Pagination;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/petTable")
public class PetTableServlet extends HttpServlet {
    private HikariDataSource dataSource = ConnectionDB.getDataSource();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int speciesId = 0;
        int age = 0;
        String gender = "";
        String searchKeyword = "";

        String speciesIdParam = request.getParameter("speciesId");
        if (speciesIdParam != null && !speciesIdParam.isEmpty())
            speciesId = Integer.parseInt(speciesIdParam);

        String ageParam = request.getParameter("age");
        if (ageParam != null && !ageParam.isEmpty()) {
            age = Integer.parseInt(ageParam);
        }

        gender = request.getParameter("gender");
        searchKeyword = request.getParameter("searchKeyword");

        Pagination pagination = new Pagination();
        PetDAO petDAO = new PetDAO(dataSource);

        // Retrieve the requested page number from the request
        String pageParam = request.getParameter("page");
        int requestedPage = 1; // Default to page 1
        if (pageParam != null && !pageParam.isEmpty()) {
            requestedPage = Integer.parseInt(pageParam);
        }

        int total = petDAO.getTotalPetCount(speciesId, age, gender, searchKeyword);
        pagination.setTotal(total);
        pagination.calculate();
        pagination.setCurrentPage(requestedPage);

        int offset = pagination.calculateOffset(requestedPage);
        int limit = pagination.getLimit();


        List<Pet> pets = petDAO.getPetListBySpecies(speciesId, age, gender, searchKeyword,offset, limit);

        request.setAttribute("pagination", pagination);
        request.setAttribute("pets", pets);
        request.getRequestDispatcher("/WEB-INF/views/petTable/petTable.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener los valores del formulario
        String nombre = request.getParameter("nameNewPet");
        String edad = request.getParameter("ageNewPet");
        String especie = request.getParameter("especie");
        String genero = request.getParameter("genderNewPet");
        String raza = request.getParameter("breedNewPet");
        String ubicacion = request.getParameter("locationNewPet");
        String descripcion = request.getParameter("descriptionNewPet");

        // Puedes imprimir los valores para verificar que se están recibiendo correctamente
        System.out.println("Nombre: " + nombre);
        System.out.println("Edad: " + edad);
        System.out.println("Especie: " + especie);
        System.out.println("Género: " + genero);
        System.out.println("Raza: " + raza);
        System.out.println("Ubicación: " + ubicacion);
        System.out.println("Descripción: " + descripcion);

        // Aquí puedes realizar las operaciones que necesites con los valores del formulario

        // Redirigir a una nueva página o hacer cualquier otra acción
//        response.sendRedirect("pagina-de-resultado.jsp");
    }

}
