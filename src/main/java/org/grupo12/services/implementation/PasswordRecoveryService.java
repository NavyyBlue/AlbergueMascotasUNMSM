package org.grupo12.services.implementation;

import org.grupo12.dao.ResetPasswordDAO;
import org.grupo12.services.interfaces.IEmailService;
import org.grupo12.services.interfaces.IPasswordRecoveryService;
import org.grupo12.util.PasswordEncryptionUtil;

public class PasswordRecoveryService implements IPasswordRecoveryService {
    private final IEmailService emailService;
    private final ResetPasswordDAO resetPasswordDAO;

    public PasswordRecoveryService(IEmailService emailService, ResetPasswordDAO resetPasswordDAO) {
        this.emailService = emailService;
        this.resetPasswordDAO = resetPasswordDAO;
    }

    public PasswordRecoveryService(ResetPasswordDAO resetPasswordDAO) {
        this.emailService = null;
        this.resetPasswordDAO = resetPasswordDAO;
    }

    @Override
    public void sendOTPByEmail(String userEmail) {
        String otp = resetPasswordDAO.generateOTPForUser(userEmail);
        if(otp != null) {
            String subject = "Recuperación de Contraseña";
            String body = "Tu código OTP para recuperación de contraseña es: " + otp;
            emailService.sendEmail(userEmail, subject, body);
        }
    }

    @Override
    public boolean verifyOtp(String otp) {
        return resetPasswordDAO.verifyOTP(otp);
    }

    @Override
    public boolean resetPassword(String otp, String newPassword) {
        String hashedPassword = PasswordEncryptionUtil.encryptPassword(newPassword);
        return resetPasswordDAO.updatePassword(otp, hashedPassword);
    }
}
