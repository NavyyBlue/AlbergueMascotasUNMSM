<%@ page import="org.grupo12.models.Pet" %>
<%@ page import="java.util.List" %>
<%@ page import="org.grupo12.dao.PetDAO" %>
<%@ page import="com.zaxxer.hikari.HikariDataSource" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Información de Mascota</title>
</head>
<body>
<h1>Información de Mascota</h1>

<%
    String petIdParam = request.getParameter("petId");
    if (petIdParam != null && !petIdParam.isEmpty()) {
        Pet petInfo = (Pet) request.getAttribute("petInfo");
        List<Pet> petStatus = (List<Pet>) request.getAttribute("petStatus");
        List<Pet> petImages = (List<Pet>) request.getAttribute("petImages");
    %>
    <div>
        <h2>Detalles de la Mascota</h2>
        <p>Nombre: <%= petInfo.getName() %></p>
        <p>Edad: <%= petInfo.getAge() %></p>
        <p>Género: <%= petInfo.getGender() %></p>
        <p>Descripción: <%= petInfo.getDescription() %></p>
    </div>
    <div>
        <h2>Estado de la Mascota</h2>
        <ul>
            <%for (Pet status : petStatus) { %>
                <li><%= status.getPetStatusName() %></li>
            <% } %>
        </ul>
    </div>

    <div>
        <h2>Imágenes de la Mascota</h2>
        <% for (Pet image : petImages) { %>
            <img src="<%= image.getImageUrl() %>" alt="Imagen de la mascota">
        <% } %>
    </div>
    <%
    } else {
        response.sendRedirect("petlist.jsp");
    }
%>
</body>
</html>