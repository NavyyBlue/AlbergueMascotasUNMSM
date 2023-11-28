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
<jsp:include page="../../components/alerts.jsp"/>
<jsp:include page="../../components/navBar.jsp"/>
<main>
    <div class="container">
        <div class="form-container">
            <h1>Formulario de Donación</h1>
            <form id="donationForm" action="${pageContext.request.contextPath}/FormDonation" method="post">
                <label for="fullname">Nombre Completo:</label>
                <input type="text" id="fullname" name="fullname" required>

                <label for="phone">Número de Teléfono:</label>
                <input type="tel" id="phone" name="phone" required>

                <label for="amount">Monto de Donación:</label>
                <input type="number" id="amount" name="amount" required>

                <label for="payment-method">Método de Pago:</label>
                <select id="payment-method" name="paymentMethod" required>
                    <option value="1">Yape</option>
                    <option value="2">Plin</option>
                    <option value="3">Transferencia BCP</option>
                </select>
                <button type="submit" class="mt-4">Donar</button>
             </form>
        </div>
        <div class="image-container">
           <img src="<%=request.getContextPath()%>/assets/img/donation/help_cat.png" alt="Imagen de donación">
        </div>
    </div>

    <script>
    // Establecer la fecha del pago como el día actual
    document.getElementById('payment-date').valueAsDate = new Date();
    </script>
</main>
</body>
</html>