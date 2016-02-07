package jsocket.util;

import java.nio.ByteBuffer;

/**
 * Abstraction for ByteBuffer to simplify int to byte conversion and vice verse
 * @author Will Czifro
 * @version 0.1.0
 */
public class ByteUtil {

    public static byte[] intToByteArray(int val, int len) {
        return ByteBuffer.allocate(len).putInt(val).array();
    }

    public static int byteArrayToInt(byte[] arr) {
        return ByteBuffer.wrap(arr).getInt();
    }
}
