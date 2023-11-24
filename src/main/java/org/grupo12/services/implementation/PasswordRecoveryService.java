package org.grupo12.services.implementation;

import org.grupo12.dao.ResetPasswordDAO;
import org.grupo12.services.interfaces.IEmailService;
import org.grupo12.services.interfaces.IPasswordRecoveryService;
import org.grupo12.util.PasswordEncryptionUtil;

import java.security.NoSuchAlgorithmException;

public class PasswordRecoveryService implements IPasswordRecoveryService {
    private final IEmailService emailService;
    private final ResetPasswordDAO resetPasswordDAO;

    public PasswordRecoveryService(IEmailService emailService, ResetPasswordDAO resetPasswordDAO) {
        this.emailService = emailService;
        this.resetPasswordDAO = resetPasswordDAO;
    }

    public  PasswordRecoveryService(ResetPasswordDAO resetPasswordDAO) {
        this.emailService = null;
        this.resetPasswordDAO = resetPasswordDAO;
    }

    @Override
    public void sendOTPByEmail(String userEmail) throws NoSuchAlgorithmException {
        String otp = resetPasswordDAO.generateOTPForUser(userEmail);
        if(otp != null) {
            String subject = "Recuperación de Contraseña";
            String body = "Tu código OTP para recuperación de contraseña es: " + otp;
            assert emailService != null;
            emailService.sendEmail(userEmail, subject, body);
        }
    }

    @Override
    public boolean verifyOtp(String otp) throws NoSuchAlgorithmException {
        String hashedOtp = PasswordEncryptionUtil.hashOTP(otp);
        return resetPasswordDAO.verifyOTP(hashedOtp);
    }

    @Override
    public boolean resetPassword(String otp, String newPassword) throws NoSuchAlgorithmException {
        String hashedPassword = PasswordEncryptionUtil.encryptPassword(newPassword);
        String hashedOtp = PasswordEncryptionUtil.hashOTP(otp);
        return resetPasswordDAO.updatePassword(hashedOtp, hashedPassword);
    }
}
