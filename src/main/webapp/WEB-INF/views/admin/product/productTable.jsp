<%@ page import="org.grupo12.models.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="org.grupo12.util.Pagination" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %><%--
  Created by IntelliJ IDEA.
  User: jccr_
  Date: 28/11/2023
  Time: 23:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Tabla de Productos</title>
        <link href="<%=request.getContextPath()%>/assets/css/productTable.css" rel="stylesheet"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/navbar.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    </head>
    <body>
        <%
            // Default speciesId if not present in the request
            int defaultProductId = 0;
            int productId = request.getParameter("productId") != null ?
                Integer.parseInt(request.getParameter("productId")) : defaultProductId;
            int active = request.getParameter("active") != null ?
                Integer.parseInt(request.getParameter("active")) : 1;
        %>
        <jsp:include page="../../../components/alerts.jsp"/>
        <jsp:include page="../../../components/navBar.jsp"/>
        <main class="mainProductTable">
            <div style="display: flex; flex-direction: column; justify-content: center; align-items: flex-end; width: 100%; margin: 0 0 20px 0;">
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editModal" data-petid="<%=defaultProductId%>">
                    Añadir Producto
                </button>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Nombre</th>
                        <th scope="col">Precio</th>
                        <th scope="col">Stock</th>
                        <th scope="col">Description</th>
                        <th scope="col">Categoría</th>
                        <th scope="col">Activo</th>
                    </tr>
                </thead>
                <tbody class="table-group-divider">
                    <%
                        List<Product> products = (List<Product>) request.getAttribute("products");
                        for (Product product : products){

                    %>
                    <tr>
                        <th scope="row"><%=product.getProductId()%></th>
                        <td><%=product.getName()%></td>
                        <td><%=product.getPrice()%></td>
                        <td><%=product.getStock()%></td>
                        <td><%=product.getDescription()%></td>
                        <td><%
                            if (product.getCategory() ==0){
                                out.print("Perro");
                            }else if (product.getCategory() == 1) {
                                out.print("Gato");
                            }else {
                                out.print("Otros");
                            }
                            %></td>
                        <td><%
                        if (product.getActive()==1){
                            out.print("Sí");
                        }else {
                            out.print("No");
                        }%></td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
        </main>
        <script>
            function submitPage(page) {
                document.getElementById('currentPage').value = page;
                document.getElementById('paginationForm').submit();
            }
        </script>
        <%
            Pagination pagination = (Pagination) request.getAttribute("pagination");
            int start = pagination.getStartPage();
            int end = pagination.getEndPage();
            int totalPages = pagination.getTotalPages(); //Total de páginas
            int currentPage = pagination.getCurrentPage();
            if(totalPages > 0){
        %>
        <!-- Controles de paginacion -->
        <nav aria-label="Page navigation example" class="d-flex justify-content-center">
            <form id="paginationForm" action="<%=request.getContextPath()%>/admin/userstable" method="get">
                <!-- Add hidden fields for other parameters if needed -->
                <input type="hidden" name="userId" value="<%=productId%>" />
                <input type="hidden" id="currentPage" name="page" value="<%=currentPage%>" />

                <ul class="pagination">
                    <li class="page-item">
                        <a class="page-link" href="#" aria-label="Previous" onclick="submitPage(<%= currentPage > start ? currentPage - 1 : start%>)">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <% for (int i = 1; i <= totalPages; i++) { %>
                    <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                        <a class="page-link" href="#" onclick="submitPage(<%=i%>)"><%=i%></a>
                    </li>
                    <% } %>
                    <li class="page-item">
                        <a class="page-link" href="#" aria-label="Next" onclick="submitPage(<%= currentPage < end ? currentPage + 1: end %>)">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </form>
        </nav>
        <%}%>
    </body>
</html>
