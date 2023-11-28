package org.grupo12.services.implementation;

import org.grupo12.dao.DonationDAO;
import org.grupo12.models.Donation;
import org.grupo12.services.interfaces.IDonationService;
import org.grupo12.services.interfaces.IEmailService;
import org.grupo12.util.PaymentMethodUtil;

public class DonationService implements IDonationService {

    private final IEmailService emailService;

    private final DonationDAO donationDAO;

    public DonationService(IEmailService emailService, DonationDAO donationDAO) {
        this.emailService = emailService;
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
}
