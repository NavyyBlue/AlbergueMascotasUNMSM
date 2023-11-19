<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Registrarse</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="<%=request.getContextPath()%>/assets/css/home.css" rel="stylesheet"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/signup.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>
</head>

<body>
<jsp:include page="../../components/navBar.jsp"/>

    <section class="d-flex h-100 h-custom mb-5">
        <div class="container py-5 h-100 align-self-stretch" style="background-color: #BBD478; border-radius: 30px;">
            <div class="row d-flex justify-content-center align-items-center  h-100">

                <div class="col-lg-10">

                    <div class="card rounded-3 h-100" style="background-color: transparent;border: transparent">

                            <h3 class="mb-4 pb-2 pb-md-0 mb-md-5 px-md-2 d-flex justify-content-center titulo" >Registro</h3>

                            <form class="px-md-2">

                                <div class="row mb-2">

                                    <div class="col-md-6 mb-4 boxWhite">
                                        <input type="text" id="FirstName" class="form-control form-control-lg" placeholder="Nombre"/>
                                    </div>

                                    <div class="col-md-6 mb-4 boxWhite">
                                        <div class="form-outline">
                                            <input type="text" id="LastName" class="form-control form-control-lg" placeholder="Apellido"/>
                                        </div>

                                    </div>
                                </div>

                                <div class="row mb-2">
                                    <div class="md-6 form-outline mb-4 boxWhite">
                                        <input type="text" id="Email" class="form-control form-control-lg" placeholder="Correo Electronico"/>
                                    </div>
                                </div>

                                <div class="row mb-2">

                                    <div class="col-md-6 mb-4 boxWhite">
                                        <input type="text" id="Password" class="form-control form-control-lg" placeholder="Contraseña"/>
                                    </div>

                                    <div class="col-md-6 mb-4 boxWhite">
                                        <div class="form-outline">
                                            <input type="text" id="PasswordConfirm" class="form-control form-control-lg" placeholder="Repetir Contraseña"/>
                                        </div>

                                    </div>
                                </div>

                                <div class="row mt-3 pt-3">
                                    <div class="col">
                                        <h3 class="subtitle">Celular</h3>
                                    </div>
                                    <div class="col">
                                        <h3 class="subtitle">Documento de Identidad</h3>
                                    </div>
                                </div>

                                <div class="row mb-2">

                                    <div class="col d-flex flex-row">
                                        <div class="col min-width-0 mr-4 justify-content-center">
                                            <h2 class="subtitle" style="color:white">+51</h2>
                                        </div>
                                        <div class="col-9 flex-fill">
                                            <div class="form-outline boxWhite">
                                                <input type="text" id="PhoneNumber" class="form-control form-control-lg" placeholder="Número"/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col d-flex flex-row">
                                        <div class="col-4 mb-4 min-width-0 ">
                                            <select class="select form-control-lg boxWhite documentos">
                                                <option value="1">DNI</option>
                                                <option value="2">Carné</option>
                                                <option value="3">Pasaporte</option>
                                            </select>
                                        </div>

                                        <div class="col-8 mb-4 boxWhite d-flex">
                                            <div class="form-outline flex-fill">
                                                <input type="text" id="Dni" class="form-control form-control-lg" placeholder="[N° Documento]"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <div class="md-6 form-outline mb-4 boxWhite">
                                        <input type="text" id="homeAdress" class="form-control form-control-lg" placeholder="Dirección"/>
                                    </div>
                                </div>

                                <div class="row justify-content-md-center">
                                    <div class="col-4 form-check order-12 mb-2">
                                        <input class="form-check-input me-2" type="checkbox" value="" id="termsConditions" />
                                        <label class="form-check-label">
                                            He leído y acepto los <a href="#!" class="text-body"><u>Términos del sistema.</u></a>
                                        </label>
                                    </div>
                                    <div class="w-100"></div>
                                    <div class="col-4 form-check order-12 mb-5">
                                        <input class="form-check-input me-2" type="checkbox" value="" id="aceptNotifs" />
                                        <label class="form-check-label">
                                            Acepto ecibir actualizaciones y ofertas en mi correo electrónico.
                                        </label>
                                    </div>
                                </div>



                                <div class="text-center">
                                    <button type="submit" class="btn btn-lg mb-1 buttonAcept">Registrarse</button>
                                </div>

                            </form>

                    </div>
                </div>
            </div>
        </div>
    </section>

</body>
</html>
