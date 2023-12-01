package org.grupo12.services.implementation;

import org.grupo12.dao.AdoptionDAO;
import org.grupo12.services.interfaces.IAdoptionService;
import org.grupo12.services.interfaces.IEmailService;

import java.security.PublicKey;
import java.util.List;

public class AdoptionService implements IAdoptionService {
    private final AdoptionDAO adoptionDAO;
    private final IEmailService emailService;

    public AdoptionService(IEmailService emailService, AdoptionDAO adoptionDAO) {
        this.emailService = emailService;
        this.adoptionDAO = adoptionDAO;
    }

    public AdoptionService(AdoptionDAO adoptionDAO) {
        this.emailService = null;
        this.adoptionDAO = adoptionDAO;
    }

    public boolean requestAdoption(int userId, int petId){
        boolean response = adoptionDAO.requestAdoption(userId, petId);
        if(response){
            String email = adoptionDAO.getUserEmail(userId);
            String subject = "Solicitud de Adopción";
            String body = "Se ha registrado tu solicitud de adopción de la mascota. Espera a que el administrador la apruebe.";
            emailService.sendEmail(email, subject, body);
        }
        return response;
    }

    public boolean completeAdoption(int userId, int petId, int userPetId) {
        List<String> emails = adoptionDAO.completeAdoption(userId, petId, userPetId);
        String petName = adoptionDAO.getPetName(petId);
        //Send emails to users
        if(emails != null) {
            for (String email : emails) {
                String subject = "Mascota Adoptada";
                String body = "La mascota "+ petName + " a la que marcaste como favorita ha sido adoptada.";
                emailService.sendEmail(email, subject, body);
            }
            return true;
        }
        return false;
    }

    public boolean rejectAdoption(int userId, int petId, int userPetId) {
        List<String> emails = adoptionDAO.rejectAdoption(userPetId, petId, userPetId);
        String petName = adoptionDAO.getPetName(userPetId);
        //Send emails to users
        if(emails != null) {
            for (String email : emails) {
                String subject = "Mascota Rechazada";
                String body = "La mascota "+ petName + " a la que marcaste como favorita ha sido rechazada.";
                emailService.sendEmail(email, subject, body);
            }
            return true;
        }
        return false;
    }
}
