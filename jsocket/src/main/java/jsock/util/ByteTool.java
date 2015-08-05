package jsock.util;

import java.nio.ByteBuffer;

/**
 * Created by czifro on 1/20/15. Used to analyze and modify a byte array, designed to be utilized with jsock.* package
 * @author William Czifro
 * @version 0.1.1
 */
public class ByteTool {

    /**
     * Creates a checksum of a byte[]
     * @param b  byte[] to checksum
     * @return   checksum value
     */
    public static int checksum(byte[] b)
    {
        int c_sum = 0;

        for (int i = 0; i < b.length; ++i)
        {
            c_sum += Byte.toUnsignedInt(b[i]);
        }

        return c_sum;
    }

    /**
     * Wraps an integer value into a byte array
     * @param val        integer value to be converted
     * @param arraySize  size of array to wrap integer
     * @return
     */
    public static byte[] intToByteArray(int val, int arraySize)
    {
        return ByteBuffer.allocate(arraySize).putInt(val).array();
    }

    /**
     * Converts byte[] to integer representation
     * @param bytes   byte[] to be converted
     * @return
     */
    public static int byteArrayToInt(byte[] bytes)
    {
        return ByteBuffer.wrap(bytes).getInt();
    }

    /**
     * Wraps an long value into a byte array
     * @param val        long value to be converted
     * @param arraySize  size of array to wrap integer
     * @return
     */
    public static byte[] longToByteArray(long val, int arraySize)
    {
        return ByteBuffer.allocate(arraySize).putLong(val).array();
    }

    /**
     * Converts byte[] to long representation
     * @param bytes   byte[] to be converted
     * @return
     */
    public static long byteArrayToLong(byte[] bytes)
    {
        return ByteBuffer.wrap(bytes).getLong();
    }
}
