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
import java.util.ArrayList;

/**
 * Created by czifro on 1/20/15. Used to analyze and modify a byte array, designed to be utilized with jsock.* package
 * @author William Czifro
 * @version 0.1.0
 */
public class ByteTool {


    /**
     *
     * @param bytes byte[] to be scanned
     * @return
     */
    public static boolean isAllNull(byte[] bytes)
    {
        for (byte b : bytes)
        {
            if (b != 0)
                return false;
        }

        return true;
    }

    public static byte[] removeNullBits(byte[] bytes)
    {
        ArrayList<Byte> newBytes = new ArrayList<Byte>();

        for (int i = 0; i < bytes.length; ++i)
        {
            if (bytes[i] != 0)
                newBytes.add(bytes[i]);
        }
        byte[] b = new byte[newBytes.size()];

        for (int i = 0; i < b.length; ++i)
        {
            b[i] = newBytes.get(i);
        }
        return b;
    }

    public static int checksum(byte[] b)
    {
        int c_sum = 0;

        for (int i = 0; i < b.length; ++i)
        {
            c_sum += Byte.toUnsignedInt(b[i]);
        }

        return c_sum;
    }

    public static byte[] intToByteArray(int val, int arraySize)
    {
        return ByteBuffer.allocate(arraySize).putInt(val).array();
    }

    public static int byteArrayToInt(byte[] bytes)
    {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static byte[] longToByteArray(long val, int arraySize)
    {
        return ByteBuffer.allocate(arraySize).putLong(val).array();
    }

    public static long byteArrayToLong(byte[] bytes)
    {
        return ByteBuffer.wrap(bytes).getLong();
    }
}
