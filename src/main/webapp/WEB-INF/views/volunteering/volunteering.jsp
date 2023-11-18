<%--
  Created by IntelliJ IDEA.
  User: jccr_
  Date: 17/11/2023
  Time: 09:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Voluntariado</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/navbar.css">
    <link href="<%=request.getContextPath()%>/assets/css/volunteering.css" rel="stylesheet"/>
</head>
<body>
<jsp:include page="../../components/navBar.jsp"/>

<div style=" display: flex; align-items: center; justify-content: center; flex-direction: column;">
    <div class="divMainVolunteering" style="border-radius: 30px;">
        <img style="width: 512px;height: 512px; border-radius: 30px"
             src="<%=request.getContextPath()%>/assets/img/volunteering/volunteer.jpg">
        <div class="divMainVolunteering" style="width: 800px; flex-direction: column;">
            <p style="font-size: 52px">Voluntariado</p>
            <p>En nuestra misión de crear un refugio cálido y acogedor, encontramos una fuerza extraordinaria en la
                colaboración y la dedicación de nuestros voluntarios. El voluntariado en nuestro albergue es más que una
                tarea; es una experiencia que transforma vidas y comunidades.</p>
            <button class="buttonVolunteer">Participar</button>
        </div>

    </div>
    <div class="volunteer-section">
        <div class="volunteer-row">
            <div class="volunteer-text">
                <h3>Compromiso Significativo</h3>
                <p>Cada acción que emprendemos deja una huella duradera en la vida de aquellos que más lo necesitan.
                    Desde proporcionar alimentos reconfortantes hasta ofrecer una palabra amable, cada voluntario
                    contribuye de manera significativa a la construcción de un entorno donde la esperanza y la
                    solidaridad florecen.</p>
            </div>
            <div class="volunteer-image">
                <img class="imgVolunteers" src="https://www.compromisorse.com/upload/noticias/027/27001/voluntario.jpg"
                     alt="Compromiso Significativo">
            </div>
        </div>

        <div class="volunteer-row">
            <div class="volunteer-image">
                <img class="imgVolunteers" src="https://res.cloudinary.com/worldpackers/image/upload/c_fill,f_auto,q_auto,w_1024/v1/guides/article_cover/wk3n9xokiyah84pr7uqv"
                     alt="Compromiso Significativo">
            </div>
            <div class="volunteer-text">
                <h3>Familia en Acción</h3>
                <p>Al unirte a nuestro equipo, te conviertes en parte de una familia dedicada y apasionada. Juntos,
                    compartimos risas, desafíos y momentos que crean lazos fuertes y duraderos. No solo construimos
                    refugios, sino que también construimos amistades que perduran toda la vida.</p>
            </div>
        </div>

        <div class="volunteer-row">
            <div class="volunteer-text">
                <h3>Inspiración Diaria</h3>
                <p>La experiencia de voluntariado aquí es una fuente constante de inspiración. Desde ver las sonrisas
                    iluminar los rostros de aquellos a quienes ayudamos hasta presenciar la resiliencia inquebrantable
                    de quienes enfrentan adversidades, cada día trae consigo lecciones valiosas y momentos que te
                    tocarán el corazón.</p>
            </div>
            <div class="volunteer-image">
                <img class="imgVolunteers" class="imgVolunteers" src="https://blog.gudog.com/wp-content/uploads/2019/08/dogs-with-bows-and-touch.jpg"
                     alt="Compromiso Significativo">
            </div>
        </div>

        <div class="volunteer-row">
            <div class="volunteer-image">
                <img class="imgVolunteers" src="https://www.prensalibre.com/wp-content/uploads/2021/08/ACE-28082021-MASCOTAS-1.jpg?quality=52" alt="Compromiso Significativo">
            </div>
            <div class="volunteer-text">
                <h3>Ayuda a buscar un hogar</h3>
                <p>Colabora en la búsqueda de soluciones permanentes para aquellos que buscan un refugio seguro. Tu
                    dedicación es la brújula que guía a aquellos que buscan un lugar para llamar hogar.</p>
            </div>
        </div>

        <div class="volunteer-row">
            <div class="volunteer-text">
                <h3>Habilidades Únicas</h3>
                <p>Todos tenemos habilidades únicas y talentos especiales. Ya sea que seas un maestro de la cocina, un
                    experto en organización de eventos o simplemente alguien con un corazón compasivo, hay un lugar para
                    ti en nuestro equipo.</p>
            </div>
            <div class="volunteer-image">
                <img class="imgVolunteers" src="https://capitalhumano.emol.com/wp-content/uploads/2016/11/trabajar_en_equipo.jpg" alt="Compromiso Significativo">
            </div>
        </div>

        <div class="volunteer-row">

            <div class="volunteer-image">
                <img class="imgVolunteers" src="https://files.nolocreocdn.com/wp-content/uploads/2020/05/b6199fe08a0f463f8257cf9515552fae.png" alt="Construye con Nosotros">
            </div>
            <div class="volunteer-text">
                <h3>Construye con Nosotros</h3>
                <p>Si tienes habilidades en construcción o manualidades, únete a nosotros en la construcción de hogares
                    que cambian vidas. Cada clavo martillado es un paso hacia un futuro más brillante.</p>
            </div>
        </div>
    </div>
</div>
</body>
</html>
