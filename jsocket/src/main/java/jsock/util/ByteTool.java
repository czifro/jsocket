/*

    Copyright (C) 2015  Czifro Development

    This file is part of the jsock.util package

    The jsock.util package is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    The jsock.util package is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with the jsock.util package.  If not, see <http://www.gnu.org/licenses/>.

 */

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
