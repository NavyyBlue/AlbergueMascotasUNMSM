package org.grupo12.websockets;

import jakarta.websocket.server.ServerEndpointConfig;
import org.grupo12.websockets.notifiers.PetFavoriteNotifier;

public class WebSocketConfigurator extends ServerEndpointConfig.Configurator{
    private static final PetFavoriteNotifier PET_FAVORITE_NOTIFIER = new PetFavoriteNotifier();

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        if (endpointClass == PetFavoriteWebSocket.class) {
            return (T) new PetFavoriteWebSocket(PET_FAVORITE_NOTIFIER);
        }
        return super.getEndpointInstance(endpointClass);
    }
}
