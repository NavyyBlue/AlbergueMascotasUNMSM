package org.grupo12.services.implementation;

import org.grupo12.dao.PetFavoriteDAO;
import org.grupo12.services.interfaces.IFavoriteService;

import java.util.List;

public class PetFavoriteService implements IFavoriteService {

    private final PetFavoriteDAO petFavoriteDAO;

    public PetFavoriteService(PetFavoriteDAO petFavoriteDAO) {
        this.petFavoriteDAO = petFavoriteDAO;
    }

    @Override
    public boolean addFavorite(int userId, int petId) {
        return petFavoriteDAO.addFavorite(userId, petId);
    }

    @Override
    public boolean removeFavorite(int userId, int petId) {
        return petFavoriteDAO.removeFavorite(userId, petId);
    }

    @Override
    public int isFavorite(int userId, int petId) {
        return petFavoriteDAO.isFavorite(userId, petId);
    }

    @Override
    public boolean updateFavorite(int userId, int petId, int isFavorite) {
        return petFavoriteDAO.updateFavorite(userId, petId, isFavorite);
    }

    @Override
    public List<Integer> getFavoritePetsByUser(int userId) {
        return petFavoriteDAO.getFavoritePetsByUser(userId);
    }

    @Override
    public int getTotalFavoritesByPet(int petId) {
        return petFavoriteDAO.getTotalFavoritesByPet(petId);
    }

    @Override
    public int getTotalFavoritesByUser(int userId) {
        return petFavoriteDAO.getTotalFavoritesByUser(userId);
    }
}
