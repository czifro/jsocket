package net.jsock;

import java.io.*;
import java.net.Socket;

/**
 * Created by czifro on 12/29/14. A wrapper for Socket connection
 * @author Will Czifro
 * @version 0.1.1
 */
public class JSocket {

    protected DataOutputStream out;
    protected DataInputStream in;
    protected Socket conn;

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
        try {
            in.read(bytes, 0, size);
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
