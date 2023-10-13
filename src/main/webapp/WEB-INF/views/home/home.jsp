<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link href="<%=request.getContextPath()%>/assets/css/home.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />

</head>
<body>

<jsp:include page="../navBar/navBar.jsp"/>
<section id="presentacion">
    <div class="img_dog_container">
        <img src="<%=request.getContextPath()%>/assets/img/home/Rectangle_4222.png" class="img-fluid" alt="perro" style="width: 400px;" >
    </div>

    <div class="section_text">
        <h1 class="section_welcome">Bienvenidos a </h1>
        <h1 class="section_refugio">REFUGIO SAN MARQUINO</h1>
        <h2>Somos un proyecto para mejorar la situación de todos los  </h2>
        <h2>animales abandonados que viven en los rincones de</h2>
        <h2>la Ciudad Universitaria. </h2>
        <div class="button_container p-6">
            <button class="button_color">SABER MÁS
                <span class="material-symbols-outlined">
                    arrow_forward
                    </span>
            </button>
        </div>
    </div>

</section>


<section id="fotos">
    <div class="container text-center">
        <div class="row align-items-start no-gutters">

            <div class="col">
                <img src="<%=request.getContextPath()%>/assets/img/home/Rectangle 4226.png" class="img-fluid" alt="gato">
            </div>
            <div class="col">
                <img src="<%=request.getContextPath()%>/assets/img/home/Rectangle 4227.png" class="img-fluid" alt="perro1">
            </div>
            <div class="col">
                <img src="<%=request.getContextPath()%>/assets/img/home/Rectangle 4228.png" class="img-fluid" alt="perro2">
            </div>
        </div>
    </div>
</section>

<section id="mapa">
    <div class="section_text2">
        <h1>Mapa Interactivo</h1>
        <p>Somos un proyecto para mejorar </p>
        <p>la situación de todos los animales </p>
        <p>abandonados que viven en los  </p>
        <p>rincones de la Ciudad </p>
        <p>Universitaria.  </p>
        <div class="button_container2">
            <button class="button_color">SABER MÁS
                <span class="material-symbols-outlined">
                arrow_forward
                </span>
            </button>
        </div>
    </div>
    <div class="img_map">

        <img src="<%=request.getContextPath()%>/assets/img/home/Group 2.png" class="img-fluid" alt="perro" style="width: 600px;" >

    </div>

</section>

</body>
</html>