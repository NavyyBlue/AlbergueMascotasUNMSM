package org.grupo12.websockets.notifiers;

import jakarta.websocket.Session;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PetFavoriteNotifier {
    private static final Set<Session> sessions = new HashSet<>();

    public static void addSession(Session session) {
        System.out.println("Adding session: " + session);
        sessions.add(session);
    }

    public static void removeSession(Session session) {
        System.out.println("Removing session: " + session);
        sessions.remove(session);
    }

    public static Set<Session> getSessions() {
        return sessions;
    }

    /*public static void notifyFavoritesChanged(int petId, int newFavoriteCount, Session initiatingSession) {
        String message = String.format("{\"petId\": %d, \"favoriteCount\": %d}", petId, newFavoriteCount);
        System.out.println("Sending message: " + message);
        for (Session session : sessions) {
            if (session.isOpen()) {
                session.getAsyncRemote().sendText(message);
            }
        }
    }*/
   /* public static void notifyFavoritesChanged(int petId, int newFavoriteCount, Session initiatingSession) {
        String message = String.format("{\"petId\": %d, \"favoriteCount\": %d}", petId, newFavoriteCount);
        System.out.println("Sending message: " + message);

        // Envía el mensaje a todas las sesiones, excepto a la sesión que inició el cambio
        for (Session session : sessions) {
            if (session.isOpen() && !session.equals(initiatingSession)) {
                session.getAsyncRemote().sendText(message);
            }
        }
    }*/

    public static void notifyFavoritesChanged(int petId, int newFavoriteCount, Session currentSession) {
        System.out.println("petId: " + petId);
        System.out.println("newFavoriteCount: " + newFavoriteCount);
        System.out.println("Sending message to all sessions except: " + currentSession);
        for (Session session : sessions) {
            System.out.println("Session: " + session);
            if (session.isOpen() && !session.equals(currentSession)) {
                try {
                    String message = String.format("{\"event\":\"favoriteChange\",\"petId\":%d,\"newFavoriteCount\":%d}", petId, newFavoriteCount);
                    System.out.println("Sending message: " + message);
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
