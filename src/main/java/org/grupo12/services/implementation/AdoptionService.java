package org.grupo12.services.implementation;

import jakarta.servlet.http.HttpServletRequest;
import org.grupo12.dao.AdoptionDAO;
import org.grupo12.models.Adoption;
import org.grupo12.services.interfaces.IAdoptionService;
import org.grupo12.services.interfaces.IEmailService;
import org.grupo12.util.AdoptionUtil;
import org.grupo12.util.Pagination;

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

    public boolean requestSponsor(int userId, int petId, double amount, int methodPayment){
        String email = adoptionDAO.requestSponsor(userId, petId, amount, methodPayment);
        String petName = adoptionDAO.getPetName(petId);
        String paymentMethod = null;
        switch (methodPayment) {
            case 0:
                paymentMethod = "Yape";
                break;
            case 1:
                paymentMethod = "Plin";
                break;
            case 2:
                paymentMethod = "Transferencia Bancaria (BCP)";
                break;
            default:
                // Manejar un caso por defecto si methodPayment no es 0, 1 ni 2.
                System.out.println("Método de pago no válido");
                break;
        }
        if(email != null) {
            String subject= "Mascota Apadrinada";
            String body = "La mascota "+petName+" fue apadrinada exitosamente!\nMonto: S./"+amount+"\nMétodo de pago: "+paymentMethod;
            emailService.sendEmail(email, subject, body);
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

    public List<Adoption> getAdoptionsPaginated(HttpServletRequest request) {
        int petStatusId = AdoptionUtil.EN_PROCESO;

        String petStatusIdParam = request.getParameter("adoptionStatusId");
        if (petStatusIdParam != null && !petStatusIdParam.isEmpty())
            petStatusId = Integer.parseInt(petStatusIdParam);

        Pagination pagination = new Pagination();

        String pageParam = request.getParameter("page");
        int requestedPage = 1; // Default to page 1
        if (pageParam != null && !pageParam.isEmpty()) {
            requestedPage = Integer.parseInt(pageParam);
        }

        int total = adoptionDAO.getTotalCountAdoption(petStatusId);
        pagination.setTotal(total);
        pagination.calculate();
        pagination.setCurrentPage(requestedPage);

        int offset = pagination.calculateOffset(requestedPage);
        int limit = pagination.getLimit();

        request.setAttribute("pagination", pagination);

        return adoptionDAO.getAdoptionsPaginated(offset, limit, petStatusId);
    }
}
