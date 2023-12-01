<%@ page import="org.grupo12.models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="org.grupo12.util.Pagination" %>
<%@ page import="org.grupo12.models.Donation" %>
<%@ page import="org.grupo12.util.PaymentMethodUtil" %>
<%@ page import="java.util.Map" %>
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
    <link href="<%=request.getContextPath()%>/assets/css/adminUser.css" rel="stylesheet"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/navbar.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
<%
    int defaultDonationId = 0;
    int donationId = request.getParameter("donationId") != null ?
            Integer.parseInt(request.getParameter("donationId")) : defaultDonationId;
    int active = request.getParameter("active") != null ?
            Integer.parseInt(request.getParameter("active")) : 1;
    Map<Integer, String> paymentMethods = PaymentMethodUtil.getPaymentMethods();
%>
<jsp:include page="../../../components/alerts.jsp"/>
<jsp:include page="../../../components/navBar.jsp"/>


<!--Droplist to change between active and not active donations -->
<div class="dropdown ms-5 mt-2">
    <label for="activeFilter" class="visually-hidden">Filtrar por estado de usuario</label>
    <select class="btn btn-secondary" id="activeFilter" onchange="window.location.href=this.value">
        <option value="<%=request.getContextPath()%>/admin/donationstable?active=2" <%=active == 2 ? "selected" : ""%> >Todos</option>
        <option value="<%=request.getContextPath()%>/admin/donationstable?active=1" <%=active == 1 ? "selected" : ""%> >Activos</option>
        <option value="<%=request.getContextPath()%>/admin/donationstable?active=0" <%=active == 0 ? "selected" : ""%> >Inactivos</option>
    </select>
</div>

<div class="table-responsive mt-3 m-5">
    <table class="table">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Nombre Donador</th>
            <th scope="col">Telefono</th>
            <th scope="col">Monto</th>
            <th scope="col">Método de pago</th>
            <th scope="col">Fecha de donación</th>
            <th scope="col">Acciones</th>
        </tr>
        </thead>
        <tbody class="table-group-divider">
        <%
            List<Donation> donations = (List<Donation>) request.getAttribute("donations");
            for (Donation donation : donations) {
        %>
        <tr>
            <th scope="row"><%=donation.getDonationId()%></th>
            <td><%=donation.getFullNameDonator()%></td>
            <td><%=donation.getPhoneNumberDonator()%></td>
            <td><%=donation.getAmount()%></td>
            <td><%=PaymentMethodUtil.getPaymentMethodName(donation.getMethodPaymentId())%></td>
            <td><%=donation.getPaymentDate()%></td>
            <td>
                <span data-bs-toggle="tooltip" data-bs-placement="top" title="Editar">
                    <a type="button" class="btn btn-warning btn-sm me-2" data-bs-toggle="modal" data-bs-target="#editModal" data-donationid="<%=donation.getDonationId()%>">
                        <img src="<%=request.getContextPath()%>/assets/svg/edit.svg" alt="editar">
                    </a>
                </span>
                <% String titleTooltip = donation.isActive() ? "Eliminar" : "Restaurar"; %>
                <span data-bs-toggle="tooltip" data-bs-placement="top" title="<%=titleTooltip%>">
                    <% if(donation.isActive()){%>
                        <a type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#deleteModal" data-donationid="<%=donation.getDonationId()%>">
                            <img src="<%=request.getContextPath()%>/assets/svg/delete.svg" alt="eliminar">
                        </a>
                    <%}else{%>
                        <a type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#restoreModal" data-donationid="<%=donation.getDonationId()%>">
                            <img src="<%=request.getContextPath()%>/assets/svg/restore.svg" alt="restaurar">
                        </a>
                    <%}%>
                </span>
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
                <h1 class="modal-title fs-5" id="editModalLabel">Editar Donación</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="editUserForm" action="${pageContext.request.contextPath}/admin/donationstable" method="post">
                    <div class="mb-3">
                        <label for="fullNameDonator" class="form-label">Nombre Donante</label>
                        <input type="text" class="form-control" id="fullNameDonator" name="fullNameDonator">
                    </div>
                    <div class="mb-3">
                        <label for="phoneNumberDonator" class="form-label">Teléfono</label>
                        <input type="text" class="form-control" id="phoneNumberDonator" name="phoneNumberDonator">
                    </div>
                    <div class="mb-3">
                        <label for="amount" class="form-label">Monto</label>
                        <input type="number" class="form-control" id="amount" name="amount">
                    </div>
                    <div class="mb-3">
                        <label for="methodPaymentId">Método de Pago:</label>
                        <select id="methodPaymentId" name="methodPaymentId">
                            <% for (Map.Entry<Integer, String> entry : paymentMethods.entrySet()) { %>
                            <option value="<%= entry.getKey() %>"><%= entry.getValue() %></option>
                            <% } %>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="paymentDate" class="form-label">Fecha de Pago</label>
                        <input type="date" class="form-control" id="paymentDate" name="paymentDate">
                    </div>
                    <div class="modal-footer">
                        <input type="hidden" id="editDonationId" name="editDonationId" value="<%=donationId%>">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        <button type="submit" class="btn btn-primary">Guardar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Delete Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="deleteModalLabel">Eliminar Donación</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="deleteUserForm" action="${pageContext.request.contextPath}/admin/donationstable" method="post">
                <div class="modal-body">
                    ¿Está seguro de eliminar el usuario?
                </div>
                <div class="modal-footer">
                    <input type="hidden" id="deleteDonationId" name="deleteDonationId" value="<%=donationId%>">
                    <input type="hidden" name="_method" value="DELETE">

                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    <button type="submit" class="btn btn-primary">Eliminar</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- Restore Modal -->
<div class="modal fade" id="restoreModal" tabindex="-1" aria-labelledby="restoreModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="restoreModalLabel">Restaurar Usuario</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="restoreUserForm" action="${pageContext.request.contextPath}/admin/donationstable" method="post">
                <div class="modal-body">
                    ¿Está seguro de restaurar el usuario?
                </div>
                <div class="modal-footer">
                    <input type="hidden" id="restoreDonationId" name="restoreDonationId" value="<%=donationId%>">
                    <input type="hidden" name="_method" value="PATCH">

                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    <button type="submit" class="btn btn-primary">Restaurar</button>
                </div>
            </form>
        </div>
    </div>
</div>

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
    <form id="paginationForm" action="<%=request.getContextPath()%>/admin/donationstable" method="get">
        <!-- Add hidden fields for other parameters if needed -->
        <input type="hidden" name="donationId" value="<%=donationId%>" />
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


    $('#deleteModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget); // Botón que activó el modal
        let donationId = button.data('donationid');
        $('#deleteDonationId').val(donationId);
    });

    $('#restoreModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget); // Botón que activó el modal
        let donationId = button.data('donationid');
        $('#restoreDonationId').val(donationId);
    });

    $('#editModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget); // Botón que activó el modal
        let donationId = button.data('donationid');

        // Parse the JSON data from the attribute
        let donationsJson = '<%= request.getAttribute("donationsJson") %>';
        let donations = JSON.parse(donationsJson);
        // Find the user with the corresponding donationId
        let donation = donations.find(function(u) {
            return u.donationId === donationId;
        });

        $('#editDonationId').val(donation.donationId);
        // Update modal fields with donation data
        $('#fullNameDonator').val(donation.fullNameDonator);
        $('#phoneNumberDonator').val(donation.phoneNumberDonator);
        $('#amount').val(donation.amount);
        $('#methodPaymentId').val(donation.methodPaymentId);
        $('#paymentDate').val(donation.paymentDate);

    });
</script>
</body>
</html>