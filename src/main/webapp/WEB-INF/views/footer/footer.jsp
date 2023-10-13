<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.Map" %>
<%
    Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
%>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>

<footer class="text-white text-center text-lg-start " style="background-color: #795234">
    <!-- Grid container -->
    <div class="container p-4">
        <!--Grid row-->
        <div class="row">
            <!--columna escritos-->
            <div class="col-lg-3 col-xl-3 col-md-12 mb-10 mb-md-0">
                <h5 class="text-uppercase">Refugio Sanmarquino</h5>

                <p>
                    Comunidad de ayuda a todos los animalitos que viven en la Universidad Nacional Mayor de San Marcos.
                </p>
            </div>
            <!--Columna escritos-->

            <!--Columna Contactos-->
            <div class="col-md-12 col-lg-5  mx-auto mb-md-0 mb-6">
                <!-- Links -->
                <h6 class="text-uppercase fw-bold mb-4">Contactanos</h6>
                <p><i class="fa fa-home me-3"></i> Lima, Peru, 01</p>
                <p>
                    <i class="fa fa-envelope me-3"></i>
                    refugioSanmarquino@gmail.com
                </p>
                <p><i class="fa fa-phone me-3"></i> + 51 943 593 676</p>
                <p><i class="fa fa-print me-3"></i> + 51 234 567 89</p>
            </div>
            <!--Columna Contactos-->
        </div>
        <!--Grid row-->
    </div>
    <!-- Grid container -->

</footer>

</body>
</html>
