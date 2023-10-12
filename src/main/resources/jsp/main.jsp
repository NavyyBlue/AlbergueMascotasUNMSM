<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.Map" %>
<%
    Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Hola</title>
    <link rel="stylesheet" href="../css/estilo.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100;0,200;1,100;1,200&display=swap"
          rel="stylesheet">
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='main.css'>
</head>
<body>
<ul>
    <li><a href="">Ampuero Donayre, Germán Alejandro</a></li>
    <li><a href="">Cardenas Ramirez, Jean Carlo</a></li>
    <li><a href="">Huerta Villalta, Jasmin Amparo</a></li>
    <li><a href="">Pairazaman Arias, Oscar Eduardo</a></li>
    <li><a href="">Cisneros Condori, Gloria Stephany</a></li>
    <li><a href="">Solano Otiniano, Lucia Alejandra</a></li>
    <li><a href="">Soria Villanueva, Miguel Stalin</a></li>
    <li><a href="">Zavala Sanchez, Diego Alonso</a></li>
</ul>
<p>Empresaurios</p>
<h3>Carreritas</h3>
<p id="especial">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dignissimos nihil quidem sed temporibus
    excepturi dolor autem magni optio, esse, aspernatur dolorum rerum, nesciunt ipsum minus. Id sint enim excepturi
    non.</p>
<p class="morado">Lorem ipsum, dolor sit amet consectetur adipisicing elit. Doloribus at sed quasi!</p>
<h4 class="moradopatodos">Lorem ipsum dolor sit, amet consectetur adipisicing elit. Reprehenderit labore reiciendis
    omnis corrupti recusandae ullam laboriosam non incidunt. Quis iusto inventore ad corrupti esse perferendis odio
    libero, dolore quo adipisci?</h4>
</body>
</html>