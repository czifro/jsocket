/*

    Copyright (C) 2015  William Czifro

    This file is part of the jsock.net package

    The jsock.net package is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    The jsock.net package is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with the jsock.net package.  If not, see <http://www.gnu.org/licenses/>.

 */

package jsock.net;

import jsock.crypto.AES;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by czifro on 12/29/14. A wrapper for Socket connection
 * @author Will Czifro
 * @version 0.2.0
 */
public class JSocket {

    protected DataOutputStream out;
    protected DataInputStream in;
    protected Socket conn;
    protected AES aes;
    protected boolean encrypt_decrypt = false;

    /**
     * Default size is 96, increase to read and write larger chunks
     */
    public int CHUNK_SIZE = 96;

    /**
     * Wraps around a Socket connection and opens I/O streams
     * @param conn A Socket connection
     * @throws java.io.IOException Throws IOException if I/O streams cannot be opened
     */
    public JSocket(Socket conn) throws IOException {
        this.conn = conn;
        out = new DataOutputStream(conn.getOutputStream());
        in = new DataInputStream(conn.getInputStream());
    }

    /**
     * Receives small message in bytes
     *
     * @return   bytes received
     */
    public byte[] recv(){
        byte[] bytes = new byte[1024];
        try {
            if (aes != null && encrypt_decrypt)
                bytes = aes.decrypt(bytes);
            in.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * Receives message of specified size in bytes
     *
     * @param size buffer size
     * @return     bytes received
     */
    public byte[] recv_all(int size)
    {
        byte[] bytes = new byte[size];
        int off = 0;
        try {
            // reads in chunks at a time
            while (in.available() > 0)
            {
                int len = (off + 96 > size ? size - off : 96);
                if (aes != null && encrypt_decrypt)
                    bytes = aes.decrypt(bytes);
                in.read(bytes, off, len);
                off += 96;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * Sends bytes
     *
     * @param b bytes to be sent
     */
    public void send(byte[] b)
    {
        try {
            if (aes != null && encrypt_decrypt)
                b = aes.encrypt(b);
            out.write(b);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends large byte[] in chunks
     *
     * @param b   Large byte[]
     * @param len length of byte[]
     */
    public void send_all(byte[] b, int len)
    {
        int off = 0;
        try {
            // writes chunks to the output stream
            while (out.size() < len)
            {
                int length = (off +CHUNK_SIZE > len ? len - off : CHUNK_SIZE);
                if (aes != null && encrypt_decrypt)
                    b = aes.encrypt(b);
                out.write(b, off, length);
                off += CHUNK_SIZE;
            }
            out.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
    }

    public void setAES(AES aes)
    {
        this.aes = aes;
    }

    public void encryptConnection(boolean useEncryption)
    {
        encrypt_decrypt = useEncryption;
    }

    public boolean connectionEncrypted()
    {
        return encrypt_decrypt;
    }

    /**
     * Closes Socket connection and input and output streams
     */
    public void close(){
        try {
            conn.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if connection has been closed
     *
     * @return    True if socket is closed, otherwise false
     */
    public boolean isClosed()
    {
        return conn.isClosed();
    }
}
