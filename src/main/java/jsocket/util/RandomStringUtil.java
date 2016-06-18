package jsocket.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Utility of creating a random string
 * @author Will Czifro
 * @version 0.1.0
 */
public final class RandomStringUtil {
    private static final SecureRandom rand = new SecureRandom();

    /**
     * Gets next random string contained within a specified number of bits
     * @param bitLength Number of bits to contain string
     * @return string full of random characters
     */
    public static String nextRandomString(int bitLength) {
        return new BigInteger(bitLength, rand).toString(32);
    }
}
