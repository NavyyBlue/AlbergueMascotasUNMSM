package org.grupo12.websockets;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.grupo12.websockets.notifiers.PetFavoriteNotifier;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(value = "/petfavorites", configurator = WebSocketConfigurator.class)
public class PetFavoriteWebSocket {

    private final PetFavoriteNotifier petFavoriteNotifier;

    public PetFavoriteWebSocket(PetFavoriteNotifier petFavoriteNotifier) {
        this.petFavoriteNotifier = petFavoriteNotifier;
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("New session: " + session);
        PetFavoriteNotifier.addSession(session);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Session closed: " + session);
        PetFavoriteNotifier.removeSession(session);
    }
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Error on session: " + session);
        throwable.printStackTrace();
    }

    /*@OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message received: " + message);
        int petId = extractPetIdFromMessage(message);
        int newFavoriteCount = extractNewFavoriteCountFromMessage(message);
        updateFavoriteCountOnUI(petId, newFavoriteCount);

        // Notifica a otros clientes sobre el cambio en los favoritos
        PetFavoriteNotifier.notifyFavoritesChanged(petId, newFavoriteCount, session);
    }

    private int extractPetIdFromMessage(String message) {
        // Implementa la lógica para extraer el petId del mensaje JSON
        // Puedes usar bibliotecas como Jackson o simplemente realizar el análisis manualmente.
        // Aquí, asumiré que el petId está representado como una cadena en el mensaje.
        // Ajusta según tu implementación.
        // Ejemplo: {"petId": '123', "newFavoriteCount": 5}
        String petIdString = message.split("\"petId\"")[1].split(":")[1].split(",")[0].trim().replaceAll("\"", "");
        return Integer.parseInt(petIdString);
    }

    private int extractNewFavoriteCountFromMessage(String message) {
        // Implementa la lógica para extraer el newFavoriteCount del mensaje JSON
        return Integer.parseInt(message.split(":")[2].split("}")[0].trim());
    }

    private void updateFavoriteCountOnUI(int petId, int newFavoriteCount) {
        // Implementa la lógica para actualizar el conteo de favoritos en la interfaz de usuario
        // Esto dependerá de cómo estés gestionando el front-end y cómo se estructura tu página.
        // Puedes utilizar JavaScript para manipular el DOM y actualizar la información en tiempo real.
        // Aquí, asumiré que ya tienes una función llamada `updateFavoriteCount` en tu script.
        String script = String.format("updateFavoriteCount(%d, %d);", petId, newFavoriteCount);
        sendScriptToClient(script);
    }

    private void sendScriptToClient(String script) {
        // Implementa la lógica para enviar el script al cliente
        // Puedes utilizar session.getAsyncRemote().sendText(script) para enviar el script a través del WebSocket.
        // Esto dependerá de cómo estés manejando la comunicación entre el back-end y el front-end.
        for (Session clientSession : PetFavoriteNotifier.getSessions()) {
            if (clientSession.isOpen()) {
                clientSession.getAsyncRemote().sendText(script);
            }
        }
    }*/
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message received: " + message);

        // Extraer información del mensaje
        int petId = extractPetIdFromMessage(message);
        int newFavoriteCount = extractNewFavoriteCountFromMessage(message);

        // Construir objeto JSON
        JsonObject jsonMessage = Json.createObjectBuilder()
                .add("event", "favoriteChange")
                .add("petId", petId)
                .add("newFavoriteCount", newFavoriteCount)
                .build();

        // Convertir objeto JSON a String
        String jsonString = jsonMessage.toString();

        // Enviar mensaje JSON al cliente
        sendJsonToClient(jsonString, session);

    // Notifica a otros clientes sobre el cambio en los favoritos
        PetFavoriteNotifier.notifyFavoritesChanged(petId, newFavoriteCount, session);
    }

    private int extractPetIdFromMessage(String message) {
        // Logica para extraer el petId del mensaje JSON

        // Ejemplo: {"petId": '123', "newFavoriteCount": 5}
        String petIdString = message.split("\"petId\"")[1].split(":")[1].split(",")[0].trim().replaceAll("\"", "");
        return Integer.parseInt(petIdString);
    }

    private int extractNewFavoriteCountFromMessage(String message) {
        return Integer.parseInt(message.split(":")[2].split("}")[0].trim());
    }

    private void sendJsonToClient(String jsonString, Session session) {
        // Implementa la lógica para enviar el JSON al cliente
        try {
            session.getBasicRemote().sendText(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
