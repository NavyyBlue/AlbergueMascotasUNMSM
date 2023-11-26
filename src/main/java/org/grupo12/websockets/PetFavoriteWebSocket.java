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
        PetFavoriteNotifier.addSession(session);
    }

    @OnClose
    public void onClose(Session session) {
        PetFavoriteNotifier.removeSession(session);
    }
    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
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
