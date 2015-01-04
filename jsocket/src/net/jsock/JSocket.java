package net.jsock;

import java.io.*;
import java.net.Socket;

/**
 * Created by czifro on 12/29/14.
 * @author Will Czifro
 * @version 0.1.0
 *
 * A wrapper for Socket connections
 */
public class JSocket {

    protected DataOutputStream out;
    protected DataInputStream in;
    protected Socket conn;

    public JSocket(Socket conn) throws IOException {
        this.conn = conn;
        out = new DataOutputStream(conn.getOutputStream());
        in = new DataInputStream(conn.getInputStream());
    }

    /**
     * Receives message from client in bytes
     *
     * @return byte[],  bytes sent by client
     */
    public byte[] recv(){
        byte[] byData = new byte[4098];
        try {
            in.read(byData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byData;
    }

    /**
     * Receives message from client in bytes
     *
     * @param size, buffer size
     * @return
     */
    public byte[] recv_all(int size)
    {
        byte[] byData = new byte[size];
        try {
            in.read(byData, 0, size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byData;
    }

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
}
