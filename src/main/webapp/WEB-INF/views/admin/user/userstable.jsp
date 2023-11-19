<%@ page import="org.grupo12.models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="org.grupo12.util.Pagination" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - Lista de Mascotas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    <link href="<%=request.getContextPath()%>/assets/css/petlist.css" rel="stylesheet"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/navbar.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<jsp:include page="../../../components/navBar.jsp"/>
<body>
    <%
        // Default speciesId if not present in the request
        int defaultUserId = 0;
        int userId = request.getParameter("userId") != null ?
                Integer.parseInt(request.getParameter("userId")) : defaultUserId;
        String editSuccessMessage = (String) request.getSession().getAttribute("editSuccess");
        String errorSuccessMessage = (String) request.getSession().getAttribute("editError");
        System.out.println("editSuccessMessage: " + editSuccessMessage);
        System.out.println("errorSuccessMessage: " + errorSuccessMessage);
    %>
    <% if(editSuccessMessage != null ) {%>
        <div class="alert alert-success" role="alert">
            <%= editSuccessMessage %>
        </div>
    <% } else if (errorSuccessMessage != null){ %>
        <div class="alert alert-danger" role="alert">
            <%= errorSuccessMessage %>
        </div>
    <% }
        //Eliminar los mensajes de la sesión después de mostrarlos
        request.getSession().removeAttribute("editSuccess");
        request.getSession().removeAttribute("editError");
    %>
    <div class="table-responsive m-5">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Nombre</th>
                <th scope="col">Apellido</th>
                <th scope="col">Email</th>
                <th scope="col">Username</th>
                <th scope="col">Telefono</th>
                <th scope="col">Rol</th>
                <th scope="col">Acciones</th>
            </tr>
            </thead>
            <tbody class="table-group-divider">
            <%
                List<User> users = (List<User>) request.getAttribute("users");
                for (User user : users) {
                    String userInfoUrl = "/admin/users/" + user.getUserId();
            %>
                    <tr>
                        <th scope="row"><%=user.getUserId()%></th>
                        <td><%=user.getFirstName()%></td>
                        <td><%=user.getLastName()%></td>
                        <td><%=user.getEmail()%></td>
                        <td><%=user.getUserName()%></td>
                        <td><%=user.getPhoneNumber()%></td>
                        <td><%
                            if (user.getUserRole() == 0) {
                                out.print("Administrador");
                            } else if (user.getUserRole() == 1) {
                                out.print("Usuario");
                            }
                            %>
                        </td>
                        <td>
                            <a type="button" class="btn btn-warning btn-sm me-2" data-bs-toggle="modal" data-bs-target="#editModal" data-userid="<%=user.getUserId()%>">
                                <img src="<%=request.getContextPath()%>/assets/svg/edit.svg" alt="editar">
                            </a>
                            <a type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#deleteModal">
                                <img src="<%=request.getContextPath()%>/assets/svg/delete.svg" alt="eliminar">
                            </a>
                        </td>
                    </tr>
                <%}%>
            </tbody>
        </table>
    </div>
    <!-- Modal -->
    <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="editModalLabel">Editar Usuario</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="editUserForm" action="${pageContext.request.contextPath}/admin/userstable" method="post">
                        <div class="mb-3">
                            <label for="editFirstName" class="form-label">Nombre</label>
                            <input type="text" class="form-control" id="editFirstName" name="editFirstName">
                        </div>
                        <div class="mb-3">
                            <label for="editLastName" class="form-label">Apellido</label>
                            <input type="text" class="form-control" id="editLastName" name="editLastName">
                        </div>
                        <div class="mb-3">
                            <label for="editEmail" class="form-label">Email</label>
                            <input type="text" class="form-control" id="editEmail" name="editEmail">
                        </div>
                        <div class="mb-3">
                            <label for="editUserName" class="form-label">UserName</label>
                            <input type="text" class="form-control" id="editUserName" name="editUserName">
                        </div>
                        <div class="mb-3">
                            <label for="editPhoneNumber" class="form-label">Telf</label>
                            <input type="text" class="form-control" id="editPhoneNumber" name="editPhoneNumber">
                        </div>
                        <div class="mb-3">
                            <label for="editUserRole" class="form-label">Rol</label>
                            <select class="form-select" id="editUserRole" name="editUserRole">
                                <option value="0">Admin</option>
                                <option value="1">Usuario</option>
                            </select>
                        </div>
                        <div class="modal-footer">
                            <input type="hidden" id="editUserId" name="editUserId" value="<%=userId%>">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            <button type="submit" class="btn btn-primary">Guardar</button>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
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
        <form id="paginationForm" action="<%=request.getContextPath()%>/petlist" method="get">
            <!-- Add hidden fields for other parameters if needed -->
            <input type="hidden" name="userId" value="<%=userId%>" />
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
    <script>
        $('#editModal').on('show.bs.modal', function (event) {
            let button = $(event.relatedTarget); // Botón que activó el modal
            let userId = button.data('userid'); // Extraer el userId del atributo data-userid

            // Parse the JSON data from the attribute
            let usersJson = '<%= request.getAttribute("usersJson") %>';
            let users = JSON.parse(usersJson);
            // Find the user with the corresponding userId
            let user = users.find(function(u) {
                return u.userId === userId;
            });

            $('#editUserId').val(user.userId);
            // Update modal fields with user data
            $('#editFirstName').val(user.firstName);
            $('#editLastName').val(user.lastName);
            $('#editEmail').val(user.email);
            $('#editUserName').val(user.userName);
            $('#editPhoneNumber').val(user.phoneNumber);
            $('#editUserRole').val(user.userRole);
        });

    </script>
</body>
</html>

