package net.jsock;

import java.io.*;
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
            in.read(bytes);
        } catch (IOException e) {
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
                in.read(bytes, off, (off + 96 > size ? size - off : 96));
                off += 96;
            }
        } catch (IOException e) {
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
            out.write(b);
            out.flush();
        } catch (IOException e) {
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
                out.write(b, off, (off +CHUNK_SIZE > len ? len - off : CHUNK_SIZE));
                off += CHUNK_SIZE;
            }
            out.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
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
