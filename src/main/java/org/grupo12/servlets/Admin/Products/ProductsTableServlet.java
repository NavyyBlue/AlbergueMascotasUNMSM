package org.grupo12.servlets.Admin.Products;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grupo12.dao.ProductDAO;
import org.grupo12.dao.UserDAO;
import org.grupo12.models.Product;
import org.grupo12.services.ProductService;
import org.grupo12.services.UserService;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/productsTable")
public class ProductsTableServlet extends HttpServlet {

    private final HikariDataSource dataSource = ConnectionDB.getDataSource();

    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService(new ProductDAO(dataSource));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       try{
           List<Product> products = productService.getProductsPaginated(request);

           Gson gson = new Gson();
           String productsJson = gson.toJson(products);

           request.setAttribute("products", products);
           request.setAttribute("productsJson", productsJson);
           request.getRequestDispatcher("/WEB-INF/views/admin/product/productTable.jsp").forward(request, response);
       }catch (Exception e){
           request.getSession().setAttribute("errorOccurred", true);
           response.sendRedirect(request.getContextPath() + "/error");
       }
    }
}
