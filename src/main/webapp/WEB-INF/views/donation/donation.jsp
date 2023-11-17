<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Donaciones</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/navbar.css">
    <link href="<%=request.getContextPath()%>/assets/css/donation.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>
</head>
<body>
<jsp:include page="../../components/navBar.jsp"/>
<main>
    <div class="mainDivText">
        <p class="mainText">Ayudanos a Ayudar</p>
        <div class="divTextDonation">
            <div class="imageDiv">
                <img src="<%=request.getContextPath()%>/assets/img/donation/img_donation.png">
            </div>
            <p style="color: #FFF;font-size: 96px; font-style: normal; font-weight: 600; ">DONACIONES</p>
        </div>

        <div style=" display: flex; flex-direction: row; align-items: center; justify-content: center;">
            <div class="divTypeDonation" style="margin: 0 13px 0 0;">
               <div class="divTextMain">
                   <p style="color: #FFF; font-size: 32px; margin: 0">BILLETERAS VIRTUALES</p>
               </div>
                <p class="textDescriptionDonation">Puedes hacer tu donación voluntaria con billeteras virtuales como son los siguientes</p>
                <img src="<%=request.getContextPath()%>/assets/img/donation/yape_plin.png">
            </div>
            <div class="divTypeDonation" style="margin: 0 0 0 13px;">
                <div class="divTextMain">
                    <p style="color: #FFF; font-size: 32px; margin: 0">CUENTAS</p>
                </div>
                <p class="textDescriptionDonation">Si deseas ayudar mediante nuestras cuentas en soles puedes utilizar los siguientes números</p>
                <img style="width: 391.139px; height: 103px; margin: 60px 0 0 0" src="<%=request.getContextPath()%>/assets/img/donation/bcp.png">
            </div>
        </div>

        <button style="width: 251px; height: 64px;border-radius: 30px; background: #94C11F; color: #FFF; margin: 10px 0">DONAR</button>
    </div>

</main>
</body>
</html>
