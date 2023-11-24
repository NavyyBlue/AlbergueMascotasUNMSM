package org.grupo12.util;
import jakarta.servlet.http.PushBuilder;
import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptionUtil {
    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public static String hashOTP(String otp) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(otp.getBytes());
        return new String(hash);
    }

    public static String generateOTP() {
        int randomPin = (int) (Math.random()*9000)+1000;
        return String.valueOf(randomPin);
    }
}
