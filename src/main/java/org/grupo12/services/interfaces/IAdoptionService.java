package org.grupo12.services.interfaces;

public interface IAdoptionService {
    public boolean requestAdoption(int userId, int petId);
    public boolean rejectAdoption(int userPetId);
}
