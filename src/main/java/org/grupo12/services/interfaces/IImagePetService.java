package org.grupo12.services.interfaces;

import org.grupo12.models.Image;

import java.util.List;

public interface IImagePetService {

    Image getMainPetImage(int petId);
    List<Image> getPetImages(int petId);
    boolean uploadPetImage(int petId, String imageUrl, boolean isMainImage);
    boolean updatePetImage(int imageId, String imageUrl, boolean Active);
    boolean deletePetImage(int imageId);
    int hadMainImage(int petId);
}
