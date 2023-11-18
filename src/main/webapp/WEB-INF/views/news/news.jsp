<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Noticias</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link href="<%=request.getContextPath()%>/assets/css/news.css" rel="stylesheet"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/navbar.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>

</head>
<body>

<jsp:include page="../../components/navBar.jsp"/>
<main>

    <div style="margin-bottom: 32.7px; display:flex; justify-content: center;">
        <div class="titleText">
            NOTICIAS
        </div>
    </div>

    <div class="row2" style="margin-bottom: 35px">
        <div class="small-12 medium-10 small-centered columns" style="margin-left: auto;">
            <h2>Últimas Noticias</h2>
        </div>
        <div class="small-12 medium-10 small-centered columns" style="justify-content: space-around">
            <ul class="featured-news-container" style="list-style-type: none">

                <li class="news-item" style="height: 498px; width: 490px; display: inline-block; margin-right: 50px;">
                    <div class="photo" style="background-image: <%=request.getContextPath()%>/assets/img/news/featurednews01.png;">
                        <a href="https://www.facebook.com/RefugioSanmarquino/posts/pfbid02GKniDPWn6QjaentancchEDz1iT2FK6PxSP5816ESbq86EskZgppjdEuQxZC2Zi9dl" target="_blank" rel="noopener noreferrer">
                            <img src="<%=request.getContextPath()%>/assets/img/news/featurednews01.png" style="width: 490px; height: 260px;">
                        </a>
                        <div class="label" style="padding-left: 5%">EMERGENCIA</div>
                    </div>
                    <div class="text">
                        <h3><a href="https://www.facebook.com/RefugioSanmarquino/posts/pfbid02GKniDPWn6QjaentancchEDz1iT2FK6PxSP5816ESbq86EskZgppjdEuQxZC2Zi9dl" target="_blank" rel="noopener noreferrer">URGENTE: Recolección pro fondos comida</a></h3>
                        <p>"Las galletas de los perritos y Michis de la universidad ya se acabó hace varios días, gracias a generosas personas que donan de kilo en kilo los animalitos tiene alguito que comer."</p>
                        <p style="text-transform: uppercase; display: inline-block;">14/11/2013 <a style="padding-left: 280px;font-weight: bold" href="https://www.facebook.com/RefugioSanmarquino/posts/pfbid02GKniDPWn6QjaentancchEDz1iT2FK6PxSP5816ESbq86EskZgppjdEuQxZC2Zi9dl" target="_blank" rel="noopener noreferrer">Leer más</a></p>
                    </div>
                </li>

                <li class="news-item" style="height: 498px; width: 490px; display: inline-block; margin-right: 10px;">
                    <div class="photo" style="background-image: <%=request.getContextPath()%>/assets/img/news/featurednews01.png;">
                        <a href="https://www.facebook.com/RefugioSanmarquino/posts/pfbid08FVnDa7LgDXAZo3dajuwWCtUS1YeE8mBjAeC7uUXXKXYDR8ezwkjkfxaXV7CPs98l" target="_blank" rel="noopener noreferrer">
                            <img src="<%=request.getContextPath()%>/assets/img/news/featurednews02.png" style="width: 490px; height: 260px;">
                        </a>
                        <div class="label" style="padding-left: 5%">ANUNCIO</div>
                    </div>
                    <div class="text">
                        <h3><a href="https://www.facebook.com/RefugioSanmarquino/posts/pfbid08FVnDa7LgDXAZo3dajuwWCtUS1YeE8mBjAeC7uUXXKXYDR8ezwkjkfxaXV7CPs98l" target="_blank" rel="noopener noreferrer">URGENTE: Busqueda de Hogar Temporal</a></h3>
                        <p>"Tenemos el caso de una perrita que fue encontrada en una de las facultades de San Marcos y es de suma urgencia encontrarle un temporal."</p>
                        <p style="text-transform: uppercase; display: inline-block;">14/11/2013 <a style="padding-left: 280px;font-weight: bold" href="https://www.facebook.com/RefugioSanmarquino/posts/pfbid08FVnDa7LgDXAZo3dajuwWCtUS1YeE8mBjAeC7uUXXKXYDR8ezwkjkfxaXV7CPs98l" target="_blank" rel="noopener noreferrer">Leer más</a></p>
                    </div>
                </li>

            </ul>

        </div>

    </div>

    <div class="row2" style="margin-bottom: 50px;margin-top: 50px">
        <div class="small-12 medium-10 small-centered columns" style="margin-left: auto;">
            <h2>Otras Noticias</h2>
        </div>

        <ul class="news-container">

            <li class="news-item" style="margin-bottom: 40px">
                <div class="container mt-4">
                    <div class="row">
                        <div class="col-md-3">

                            <div class="photo" style="background-image: url('<%=request.getContextPath()%>/assets/img/news/news01.png'); width: 270px;">
                                <a href="#">
                                    <img src="<%=request.getContextPath()%>/assets/img/news/news01.png" style="width: 270px;">
                                </a>
                                <div class="label" style="width: 270px;">ANUNCIO</div>
                            </div>
                        </div>

                        <div class="col-md-9">

                            <div class="text" style="vertical-align: center">
                                <h3><a href="#">URGENTE: Medicina para Hiena</a></h3>
                                <p>"Urge comprarle su medicamento a Hiena, la perrita toma pastillas para sus articulaciones ya que tiene problemas para caminar producto de su edad."</p>
                                <p style="text-transform: uppercase; display: inline-block;">29/09/2013 <a style="padding-left: 400px; font-weight: bold;" href="#">Leer más</a></p>
                            </div>
                        </div>
                    </div>
                </div>
            </li>

            <li class="news-item" style="margin-bottom: 40px">
                <div class="container mt-4">
                    <div class="row">
                        <div class="col-md-3">

                            <div class="photo" style="background-image: url('<%=request.getContextPath()%>/assets/img/news/news02.png'); width: 270px;">
                                <a href="#">
                                    <img src="<%=request.getContextPath()%>/assets/img/news/news02.png" style="width: 270px;">
                                </a>
                                <div class="label" style="width: 270px;">ANUNCIO</div>
                            </div>
                        </div>

                        <div class="col-md-9">

                            <div class="text" style="vertical-align: center">
                                <h3><a href="#">URGENTE: Se acaba el tiempo</a></h3>
                                <p>"Necesitamos un hogar temporal urgente para esta perrita. Ya nadie puede cuidarla, solo hasta hoy."</p>
                                <p style="text-transform: uppercase; display: inline-block;">29/09/2013 <a style="padding-left: 400px; font-weight: bold;" href="#">Leer más</a></p>
                            </div>
                        </div>
                    </div>
                </div>
            </li>

            <li class="news-item" style="margin-bottom: 40px">
                <div class="container mt-4">
                    <div class="row">
                        <div class="col-md-3">

                            <div class="photo" style="background-image: url('<%=request.getContextPath()%>/assets/img/news/news03.png'); width: 270px;">
                                <a href="#">
                                    <img src="<%=request.getContextPath()%>/assets/img/news/news03.png" style="width: 270px;">
                                </a>
                                <div class="label" style="width: 270px;">ACTUALIZACION</div>
                            </div>
                        </div>

                        <div class="col-md-9">

                            <div class="text" style="vertical-align: center">
                                <h3><a href="#">ANUNCIO: Caso Atun</a></h3>
                                <p>"Luego de una cirugía y semanas de recuperación, Atuncita fue adoptada por una egresada de la especialidad de Ortodoncia de la facultad de Odontología."</p>
                                <p style="text-transform: uppercase; display: inline-block;">29/09/2013 <a style="padding-left: 400px; font-weight: bold;" href="#">Leer más</a></p>
                            </div>
                        </div>
                    </div>
                </div>
            </li>

        </ul>

    </div>

</main>
<jsp:include page="../../components/footer.jsp"/>

</body>
</html>