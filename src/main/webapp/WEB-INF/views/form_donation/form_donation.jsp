<%@ page import="org.grupo12.util.PaymentMethodUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<%=request.getContextPath()%>/assets/css/form_donation.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/navbar.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>
    <title>Formulario de Donación</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
<jsp:include page="../../components/alerts.jsp"/>
<jsp:include page="../../components/navBar.jsp"/>
<%
    Map<Integer, String> paymentMethods = PaymentMethodUtil.getPaymentMethods();
%>
<main>
    <div class="container">
        <div class="form-container">
            <h1>Formulario de Donación</h1>
            <form id="donationForm" action="${pageContext.request.contextPath}/FormDonation" method="post">
                <label for="fullname">Nombre Completo:</label>
                <input type="text" id="fullname" name="fullname" placeholder="Ingrese nombre completo" required>

                <label for="phone">Número de Teléfono:</label>
                <input type="tel" id="phone" name="phone" placeholder="Ejm: 954237821" required>

                <label for="amount">Monto de Donación:</label>
                <input type="number" id="amount" name="amount" placeholder="Ingrese monto en soles" required>

                <label for="payment-method">Método de Pago:</label>
                <select id="payment-method" name="paymentMethod" required>
                    <% for (Map.Entry<Integer, String> entry : paymentMethods.entrySet()) { %>
                    <option value="<%= entry.getKey() %>"><%= entry.getValue() %></option>
                    <% } %>
                </select>
                <button type="submit" class="mt-4">Donar</button>
             </form>
        </div>
        <div class="image-container">
           <img src="<%=request.getContextPath()%>/assets/img/donation/help_cat.png" alt="Imagen de donación">
        </div>
    </div>
</main>
</body>
</html>