package org.daniel.prokop.dev.DAO.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

public class Hash {
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 512;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";

    public static Optional<String> hashPassword(String password, Optional<String> salt){
        char[] chars = password.toCharArray();
        byte[] bytes = salt.get().getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars,bytes,ITERATIONS,KEY_LENGTH);

        Arrays.fill(chars,Character.MIN_VALUE);

        try{
            SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] securePassword = fac.generateSecret(spec).getEncoded();
            return Optional.of(Base64.getEncoder().encodeToString(securePassword));
        }catch(NoSuchAlgorithmException | InvalidKeySpecException ex){
            System.err.println("Exception encountered in hashPassword(");
            return Optional.empty();
        }
    }
}

