<%--
  Created by IntelliJ IDEA.
  User: jccr_
  Date: 17/11/2023
  Time: 06:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Detalles del Producto</title>
    <link href="<%=request.getContextPath()%>/assets/css/detailproduct.css" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <div class="product-details" id="productDetails">
        <button class="close-button" onclick="closeProductDetails()">🗙</button>
        <div class="product-info">
            <h1 id="productName">Nombre del Producto</h1>
            <p id="productPrice">Precio: S/0.00</p>
            <p id="productStock">Stock disponible: 0 unidades</p>
            <p id="productCategory">Categoría: Alimento </p>
            <p id="productActive">Disponible: Sí</p>
        </div>
        <div class="product-image">
            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSqFmlxH8IPN4zVQ7J40RD7pSLsFGRI3Y4udA&usqp=CAU" alt="Producto">
        </div>
    </div>
</div>

<script>
    function showProductDetails() {
        var productDetails = document.getElementById("productDetails");
        productDetails.style.display = "flex";
    }

    function closeProductDetails() {
        var productDetails = document.getElementById("productDetails");
        productDetails.style.display = "none";
    }

    // Simula el clic en un producto para mostrar los detalles (esto puede variar según tu implementación real)
    document.addEventListener("DOMContentLoaded", function() {
        // Simula el clic en un producto después de que la página se haya cargado
        showProductDetails();
    });
</script>
</body>
</html>
