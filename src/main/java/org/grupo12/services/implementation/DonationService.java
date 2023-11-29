package org.grupo12.services.implementation;

import jakarta.servlet.http.HttpServletRequest;
import org.grupo12.dao.DonationDAO;
import org.grupo12.models.Donation;
import org.grupo12.services.interfaces.IDonationService;
import org.grupo12.services.interfaces.IEmailService;
import org.grupo12.util.Pagination;
import org.grupo12.util.PaymentMethodUtil;

import java.util.List;

public class DonationService implements IDonationService {

    private final IEmailService emailService;

    private final DonationDAO donationDAO;

    public DonationService(IEmailService emailService, DonationDAO donationDAO) {
        this.emailService = emailService;
        this.donationDAO = donationDAO;
    }

    public DonationService(DonationDAO donationDAO) {
        this.emailService = null;
        this.donationDAO = donationDAO;
    }

    @Override
    public boolean addDonation(Donation donation) {
        boolean result = donationDAO.addDonation(donation);
        String methodPaymentName = PaymentMethodUtil.getPaymentMethodName(donation.getMethodPaymentId());
        if(result) {
            String email = "oscar.pairazaman.prueba@gmail.com";
            String subject = "Donación";
            String body = donation.getFullNameDonator() + " desea realizar una donación\n" +
                          "Monto: S/." + donation.getAmount() +
                          "\nMétodo de pago: " + methodPaymentName +
                          "\nTelefono: " + donation.getPhoneNumberDonator() +
                          "\nContactar a la brevedad para coordinar la donación.";

            emailService.sendEmail(email, subject, body);
        }
        return result;
    }

    @Override
    public boolean updateDonation(Donation donation) {
        return donationDAO.updateDonation(donation);
    }

    public List<Donation> getDonationsPaginated(HttpServletRequest request) {
        int donationId = 0;
        int active = 1;

        String donationIdParam = request.getParameter("donationId");
        if (donationIdParam != null && !donationIdParam.isEmpty())
            donationId = Integer.parseInt(donationIdParam);

        String activeParam = request.getParameter("active");
        if (activeParam != null && !activeParam.isEmpty())
            active = Integer.parseInt(activeParam);

        Pagination pagination = new Pagination();

        // Retrieve the requested page number from the request
        String pageParam = request.getParameter("page");
        int requestedPage = 1; // Default to page 1
        if (pageParam != null && !pageParam.isEmpty()) {
            requestedPage = Integer.parseInt(pageParam);
        }

        int total = donationDAO.getTotalCountDonation(active);
        pagination.setTotal(total);
        pagination.calculate();
        pagination.setCurrentPage(requestedPage);

        int offset = pagination.calculateOffset(requestedPage);
        int limit = pagination.getLimit();

        request.setAttribute("pagination", pagination);

        return donationDAO.getDonationsPaginated(donationId, active, offset, limit);
    }

    public boolean deleteDonation(int donationId) {
        return donationDAO.deleteDonation(donationId);
    }

    public boolean restoreDonation(int donationId) {
        return donationDAO.restoreDonation(donationId);
    }
}
