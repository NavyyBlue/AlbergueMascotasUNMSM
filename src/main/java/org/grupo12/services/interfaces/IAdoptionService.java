package org.grupo12.services.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import org.grupo12.models.Adoption;
import org.grupo12.models.Pet;
import org.grupo12.models.UserPet;

import java.util.List;

public interface IAdoptionService {
    public boolean requestAdoption(int userId, int petId);
    public boolean completeAdoption(int userId, int petId, int userPetId);
    public boolean rejectAdoption(int userId, int petId, int userPetId);
    public List<Adoption> getAdoptionsPaginated(HttpServletRequest request);
    public int getTotalAdoptionsByUser(int userId);
    public List<UserPet>  getPetAdoptionsByUser(int userId);
}
