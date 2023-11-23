package org.grupo12.services.implementation;

import org.grupo12.services.UserService;
import org.grupo12.services.interfaces.IEmailService;
import org.grupo12.services.interfaces.IPasswordRecoveryService;
import org.grupo12.util.PasswordEncryptionUtil;

public class PasswordRecoveryService implements IPasswordRecoveryService {
    private final IEmailService emailService;
    private final UserService userService;

    public PasswordRecoveryService(IEmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }
    @Override
    public void sendOTPByEmail(String userEmail) {
        if(userService.verifyUserExistenceByEmail(userEmail)){
            String otp = PasswordEncryptionUtil.generateOTP();

            String subject = "Recuperación de Contraseña";
            String body = "Tu código OTP para recuperación de contraseña es: " + otp;
            emailService.sendEmail(userEmail, subject, body);
        }
    }
}
