package org.daniel.prokop.dev.DAO.util;

import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;


public class GenerateSalt {

    public static String getSalt()
            throws NoSuchAlgorithmException, NoSuchProviderException
    {
        // Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");

        // Create array for salt
        byte[] salt = new byte[16];

        // Get a random salt
        sr.nextBytes(salt);

        // return salt
        return Arrays.toString(salt);
    }
}
