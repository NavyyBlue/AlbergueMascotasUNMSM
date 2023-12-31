<%--
  Created by IntelliJ IDEA.
  User: jccr_
  Date: 17/11/2023
  Time: 10:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Nosotros</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/navbar.css">
    <link href="<%=request.getContextPath()%>/assets/css/about.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>
<jsp:include page="../../components/navBar.jsp"/>
<div class="container">
    <section id="nosotros">
        <div class="title-box">
            <h1>Nosotros</h1>
        </div>
        <div class="content">
            <div class="text">
                <p>
                    Bienvenido a Refugio Sanmarquino, un proyecto iniciado por alumnas y egresadas de la Facultad de Letras y Ciencias Humanas de la UNMSM.
                    Nos dedicamos a mejorar la situación de los animales abandonados en la Ciudad Universitaria.
                    Nuestro objetivo es fomentar la participación de los alumnos en el cuidado de nuestros hermanos menores.
                </p>
                <p>
                    No somos un albergue, sino un grupo comprometido con la manutención y bienestar de los seres de cuatro patas de la UNMSM.
                    Desde la provisión de comida hasta el transporte al veterinario, cada aporte cuenta.
                </p>
                <p>
                    Gracias al apoyo económico, logramos adquirir abrigadoras chompas para los perritos sanmarquinos y proporcionar comida especial a Perrovaca,
                    además de sus revisiones veterinarias regulares. Imagina lo que podríamos lograr si todos nos unimos a esta noble causa.
                    Somos miles de sanmarquinos y juntos podemos marcar la diferencia.
                </p>
                <p>
                    Nuestro compromiso se extiende a la educación y concientización sobre el cuidado de los animales.
                    Organizamos eventos y charlas para involucrar a más personas en la protección y amor hacia nuestros amigos de cuatro patas.
                </p>
            </div>
            <div class="image-box">
                <img src="https://i.postimg.cc/Dz9K7Gn9/cat-us.jpg" alt="Imagen de estudiantes cuidando animales">
            </div>
        </div>
    </section>
</div>
</body>
</html>
