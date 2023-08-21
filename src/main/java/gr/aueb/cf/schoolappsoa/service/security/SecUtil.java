package gr.aueb.cf.schoolappsoa.service.security;

import org.mindrot.jbcrypt.BCrypt;

/**
 * This utility class hashes the password
 * using jBCrypt library.
 *
 * @author Thanasis Chousiadas
 */
public class SecUtil {

    /**
     * No instances of this class should be available.
     */
    private SecUtil() {
    }

    /**
     * This utility method hashes the input password, given by the user.
     *
     * @param inputPasswd a String with the input password.
     * @return a hashed String.
     */
    public static String hashPassword(String inputPasswd) {
        int workload = 12;
        String salt = BCrypt.gensalt(workload);
        return BCrypt.hashpw(inputPasswd, salt);
    }

    /**
     * This utility method checks if two hashed passwords are the
     * same.
     *
     * @param inputPasswd  a String with the input password given by the user.
     * @param hashedPasswd a String with the password to be compared.
     * @return true if the two passwords are the same,
     * otherwise false.
     */
    public static boolean checkPassword(String inputPasswd, String hashedPasswd) {
        return BCrypt.checkpw(inputPasswd, hashedPasswd);
    }
}
