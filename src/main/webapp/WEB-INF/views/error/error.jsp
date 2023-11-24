<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="text-center">
        <h1 class="text-danger">Error</h1>
        <p class="lead">Lo sentimos, se ha producido un error en la aplicación.</p>
        <p class="lead">Regrese a la vista principal.</p>
        <a class="btn btn-primary" href="<%=request.getContextPath()%>/home">Volver</a>
    </div>
    <div class="mt-5 text-center">
        <img width="500px" alt="error" src="<%=request.getContextPath()%>/assets/img/cat_error.jpg"/>
    </div>
</div>
</body>
</html>
