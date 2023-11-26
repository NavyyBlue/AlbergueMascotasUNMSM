package org.grupo12.websockets.notifiers;

import jakarta.websocket.Session;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PetFavoriteNotifier {
    private static final Set<Session> sessions = new HashSet<>();

    public static void addSession(Session session) {
        sessions.add(session);
    }

    public static void removeSession(Session session) {
        sessions.remove(session);
    }

    public static Set<Session> getSessions() {
        return sessions;
    }

   public static void notifyFavoritesChanged(int petId, int newFavoriteCount, Session currentSession) {
        for (Session session : sessions) {
            if (session.isOpen() && !session.equals(currentSession)) {
                try {
                    String message = String.format("{\"event\":\"favoriteChange\",\"petId\":%d,\"newFavoriteCount\":%d}", petId, newFavoriteCount);
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
