<%@ page import="java.util.Map" %>
<style>
    .fade-alert {
        display: none;
        position: relative;
    }

    .fade-alert.show {
        display: block;
        animation: slideInRight 0.2s ease-in-out;
    }

    @keyframes slideInRight {
        from {
            opacity: 0;
            transform: translateX(-50px);
        }
        to {
            opacity: 1;
            transform: translateX(0);
        }
    }
</style>
<%
    // Recibe un parámetro "alerts" que es un Map<String, String> donde la clave es el tipo de alerta (success, error, etc.)
    // y el valor es el mensaje de la alerta.
    Map<String, String> alerts = (Map<String, String>) request.getSession().getAttribute("alerts");
    if (alerts != null && !alerts.isEmpty()) {
        for (Map.Entry<String, String> entry : alerts.entrySet()) {
            String alertType = entry.getKey();
            String alertMessage = entry.getValue();
            %>
            <div class="fade-alert show position-relative alert alert-<%= alertType %> position-absolute top-0 start-0 m-4 z-3" role="alert">
                <%= alertMessage %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <%
        }
    }

    // Elimina el atributo de la request para que no se muestre la alerta en la siguiente página
    request.getSession().removeAttribute("alerts");
%>
<script>
    // Función para cerrar la alerta automáticamente después de unos segundos
    document.addEventListener('DOMContentLoaded', function () {
        setTimeout(function() {
            $('.fade-alert').alert('close');
        }, 3000);
    });
</script>