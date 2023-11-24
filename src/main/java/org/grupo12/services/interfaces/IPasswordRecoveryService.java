package org.grupo12.services.interfaces;

public interface IPasswordRecoveryService {
    void sendOTPByEmail(String userEmail);
    boolean verifyOtp(String otp);
    boolean resetPassword(String otp, String newPassword);
}
