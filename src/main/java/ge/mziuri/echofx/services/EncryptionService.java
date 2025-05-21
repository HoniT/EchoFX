package ge.mziuri.echofx.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionService {
    public static String encryptPassword(String password) {
        try {
            // Hashing the password with Message Digest SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes());
            // Converting hashed password into a string builder and returning it
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Encryption algorithm not found", e);
        }
    }
}
