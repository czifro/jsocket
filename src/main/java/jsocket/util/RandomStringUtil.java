package jsocket.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Will Czifro
 */
public final class RandomStringUtil {
    private static final SecureRandom rand = new SecureRandom();

    public static String nextRandomString(int bitLength) {
        return new BigInteger(bitLength, rand).toString(32);
    }
}
