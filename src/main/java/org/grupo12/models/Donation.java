package org.grupo12.models;

import lombok.Data;

@Data
public class Donation {
    private int donationId;
    private String fullNameDonator;
    private String phoneNumberDonator;
    private float amount;
    private int methodPaymentId;
    private String paymentDate;
    private boolean active;
}
