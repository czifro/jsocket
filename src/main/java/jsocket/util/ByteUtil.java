package jsocket.util;

import java.nio.ByteBuffer;

/**
 * Abstraction for ByteBuffer to simplify int to byte conversion and vice verse
 * @author Will Czifro
 * @version 0.1.0
 */
public final class ByteUtil {

    /**
     * Converts an integer to a byte array of specified length
     * @param val the integer value to be converted
     * @param len the length of byte array
     * @return a byte array containing an integer value
     */
    public static byte[] intToByteArray(int val, int len) {
        return ByteBuffer.allocate(len).putInt(val).array();
    }

    /**
     * Converts a byte array to an integer
     * @param arr the byte array containing an integer value
     * @return the extracted integer
     */
    public static int byteArrayToInt(byte[] arr) {
        return ByteBuffer.wrap(arr).getInt();
    }
}
