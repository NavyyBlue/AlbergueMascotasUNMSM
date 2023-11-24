<%--
  Created by IntelliJ IDEA.
  User: jasmi
  Date: 18/11/2023
  Time: 17:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<%=request.getContextPath()%>/assets/css/form_donation.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/navbar.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>
    <title>Formulario de Donación</title>
</head>
<body>
<jsp:include page="../../components/navBar.jsp"/>
<main>
    <div class="container">
        <div class="form-container">
        <h1>Formulario de Donación</h1>
        <form id="donationForm" action="procesar_donacion.php" method="post">
            <label for="fullname">Nombre Completo:</label>
            <input type="text" id="fullname" name="fullname" required>

            <label for="phone">Número de Teléfono:</label>
            <input type="tel" id="phone" name="phone" required>

            <label for="amount">Monto de Donación:</label>
            <input type="number" id="amount" name="amount" required>

            <label for="payment-method">Método de Pago:</label>
            <select id="payment-method" name="payment-method" required>
                <option value="yape">Yape</option>
                <option value="plin">Plin</option>
                <option value="transferencia">Transferencia BCP</option>
            </select>

            <label for="number-to-donate">Número a Donar:</label>
            <input type="text" id="number-to-donate" name="number-to-donate" value="123456789" readonly>

            <label for="payment-date">Fecha de Pago:</label>
            <input type="date" id="payment-date" name="payment-date" required>

            <button type="submit">Donar</button>
         </form>
        </div>
        <div class="image-container">
           <img src="https://scontent-lim1-1.xx.fbcdn.net/v/t39.30808-6/395109050_719067953583602_6051584633296551983_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeH_po6L4NA4If2msN5m-7g3WDaGWiADYX1YNoZaIANhfWPNv4hTzlDASwPaho8LI1R6MFgJfRm4Qf12nox9QRyu&_nc_ohc=G9DkD6P-NGYAX-WseXK&_nc_ht=scontent-lim1-1.xx&cb_e2o_trans=t&oh=00_AfBmzvdTN9tfdn_qsxhaR9ZxCQPRtfu9IOIYqfdGyIg23A&oe=655E6742" alt="Imagen de donación">
        </div>
    </div>

    <script>
    // Establecer la fecha del pago como el día actual
    document.getElementById('payment-date').valueAsDate = new Date();
    </script>
</main>
</body>
</html>