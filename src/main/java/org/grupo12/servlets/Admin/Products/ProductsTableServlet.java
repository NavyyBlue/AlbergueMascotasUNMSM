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
import org.grupo12.models.Pet;
import org.grupo12.models.Product;
import org.grupo12.services.ProductService;
import org.grupo12.services.UserService;
import org.grupo12.util.ConnectionDB;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("_method");
        System.out.println("METODO: "+method);
        if ("DELETE".equalsIgnoreCase(method)) {
            handleDeleteRequest(request, response);
        } else if ("PATCH".equalsIgnoreCase(method)) {
            handleRestoreRequest(request, response);
        } else {
            String editPetId = request.getParameter("editProductId");
            if (Objects.equals(editPetId, "")){
                handleInsertRequest(request, response);
            }else{
                handleUpdateRequest(request, response);
            }
        }
    }

    private void handleInsertRequest(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String editName = request.getParameter("editName");
        int editPrice = Integer.parseInt(request.getParameter("editPrice"));
        int editStock = Integer.parseInt(request.getParameter("editStock"));
        String editDescription = request.getParameter("editDescription");
        int editCategory = Integer.parseInt(request.getParameter("editCategory"));


        Product insertProduct = new Product();
        insertProduct.setName(editName);
        insertProduct.setPrice(editPrice);
        insertProduct.setStock(editStock);
        insertProduct.setDescription(editDescription);
        insertProduct.setCategory(editCategory);

        boolean success = productService.createProduct(insertProduct);

        handleOperationResult(success, request, response);
    }

    private void handleUpdateRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int editProductId = Integer.parseInt(request.getParameter("editProductId"));
        String editName = request.getParameter("editName");
        int editPrice = Integer.parseInt(request.getParameter("editPrice"));
        int editStock = Integer.parseInt(request.getParameter("editStock"));
        String editDescription = request.getParameter("editDescription");
        int editCategory = Integer.parseInt(request.getParameter("editCategory"));

        System.out.println(editProductId);
        System.out.println(editName);
        System.out.println(editPrice);
        System.out.println(editStock);
        System.out.println(editDescription);
        System.out.println(editCategory);
        Product insertProduct = new Product();
        insertProduct.setProductId(editProductId);
        insertProduct.setName(editName);
        insertProduct.setPrice(editPrice);
        insertProduct.setStock(editStock);
        insertProduct.setDescription(editDescription);
        insertProduct.setCategory(editCategory);

        boolean success = productService.updateProduct(insertProduct);

        handleOperationResult(success, request, response);
    }

    private void handleDeleteRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("deleteProductId"));
        boolean success = productService.deleteProduct(productId);

        handleOperationResult(success, request, response);
    }

    private void handleRestoreRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("restoreProductId"));
        boolean success = productService.restoreProduct(productId);

        handleOperationResult(success, request, response);
    }

    private void handleOperationResult(boolean success, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (success) {
            request.getSession().setAttribute("alerts", Collections.singletonMap("success", "La operación se realizó con éxito"));
        } else {
            request.getSession().setAttribute("alerts", Collections.singletonMap("danger", "Hubo un error al procesar la solicitud"));
        }
        response.sendRedirect(request.getContextPath() + "/admin/productsTable");
    }
}
