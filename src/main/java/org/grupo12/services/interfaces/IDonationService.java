package org.grupo12.services.interfaces;

import org.grupo12.models.Donation;

public interface IDonationService {
    boolean addDonation(Donation donation);
    boolean updateDonation(Donation donation);
}
