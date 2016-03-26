package jsocket.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Will Czifro
 */
public final class RandomStringUtil {
    private static final SecureRandom rand = new SecureRandom();

    public static String nextRandomString() {
        return new BigInteger(130, rand).toString(32);
    }
}
