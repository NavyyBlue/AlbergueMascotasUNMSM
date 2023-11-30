package org.grupo12.services.implementation;

import org.grupo12.dao.PetImageDAO;
import org.grupo12.models.Image;
import org.grupo12.services.interfaces.IImagePetService;

import java.util.List;

public class ImagePetService implements IImagePetService {
    private final PetImageDAO petImageDAO;

    public ImagePetService(PetImageDAO petImageDAO) {
        this.petImageDAO = petImageDAO;
    }

    @Override
    public Image getMainPetImage(int petId) {
        return petImageDAO.getMainPetImage(petId);
    }

    @Override
    public List<Image> getPetImages(int petId) {
        return petImageDAO.getPetImages(petId);
    }

    @Override
    public boolean uploadPetImage(int petId, String imageUrl, boolean isMainImage) {
        return petImageDAO.uploadPetImage(petId, imageUrl, isMainImage);
    }

    @Override
    public boolean updatePetImage(int imageId, String imageUrl, boolean isMainImage) {
        return petImageDAO.updatePetImage(imageId, imageUrl, isMainImage);
    }

    @Override
    public boolean deletePetImage(int imageId) {
        return petImageDAO.deletePetImage(imageId);
    }

}
