package org.grupo12.util;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptionUtil {
    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public static String hashOTP(String otp){
        return BCrypt.hashpw(otp, BCrypt.gensalt());
    }

    public static String generateOTP() {
        int randomPin = (int) (Math.random()*9000)+1000;
        return String.valueOf(randomPin);
    }
}
