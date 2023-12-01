package org.grupo12.services.interfaces;

public interface IAdoptionService {
    public boolean requestAdoption(int userId, int petId);
    public boolean completeAdoption(int userId, int petId, int userPetId);
    public boolean rejectAdoption(int userId, int petId, int userPetId);
}
