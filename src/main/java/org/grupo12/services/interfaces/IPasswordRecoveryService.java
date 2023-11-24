package org.grupo12.services.interfaces;

import java.security.NoSuchAlgorithmException;

public interface IPasswordRecoveryService {
    boolean sendOTPByEmail(String userEmail) throws NoSuchAlgorithmException;
    boolean verifyOtp(String otp) throws NoSuchAlgorithmException;
    boolean resetPassword(String otp, String newPassword) throws NoSuchAlgorithmException;
}
