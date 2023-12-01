package org.grupo12.services.interfaces;

import java.util.List;

public interface IFavoriteService {
    boolean addFavorite(int userId, int petId);
    boolean removeFavorite(int userId, int petId);
    boolean updateFavorite(int userId, int petId, int isFavorite);
    int isFavorite(int userId, int petId);
    List<Integer> getFavoritePetsByUser(int userId);
    int getTotalFavoritesByPet(int userId);
}
