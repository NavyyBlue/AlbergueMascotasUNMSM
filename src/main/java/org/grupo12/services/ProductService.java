package org.grupo12.services;

import jakarta.servlet.http.HttpServletRequest;
import org.grupo12.dao.ProductDAO;
import org.grupo12.models.Pet;
import org.grupo12.models.Product;
import org.grupo12.util.Pagination;

import java.util.List;

public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getProductsPaginated(HttpServletRequest request){
        int productId = 0;
        int active = 2;
        String productIdParam = request.getParameter("productId");
        if (productIdParam != null && !productIdParam.isEmpty())
            productId = Integer.parseInt(productIdParam);

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

        int total = productDAO.getTotalCountProducts(active);
        pagination.setTotal(total);
        pagination.calculate();
        pagination.setCurrentPage(requestedPage);

        int offset = pagination.calculateOffset(requestedPage);
        int limit = pagination.getLimit();

        request.setAttribute("pagination", pagination);

        return productDAO.getProducts(productId, active, offset, limit);
    }

    public boolean createProduct(Product product){
        return productDAO.createProduct(product);
    }
    public boolean updateProduct(Product product) {
        return productDAO.updateProduct(product);
    }

    public boolean deleteProduct(int productId) {return productDAO.deleteProduct(productId);}
    public boolean restoreProduct(int productId) {return productDAO.restoreProduct(productId);}
}
