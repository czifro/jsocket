package net.jsock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Created by czifro on 12/29/14.
 * @author Will Czifro
 * @version 0.1.0
 *
 * A wrapper for Socket connections
 */
public class JSocket {

    protected PrintWriter writer;
    protected BufferedReader reader;
    protected Socket conn;

    public JSocket(Socket conn) throws IOException {
        this.conn = conn;
        writer = new PrintWriter(this.conn.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(this.conn.getInputStream()));
    }

    /**
     * Receives message from client in bytes
     *
     * @return byte[],  bytes sent by client
     */
    public byte[] recv(){
        String msg = "";
        char[] data = new char[2048];
        byte[] byData = new byte[4096];
        try{
            reader.read(data);     // reads bytes into char[]
            ByteBuffer.wrap(byData).asCharBuffer().put(data);  // converts char[] to byte[]
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
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
        char[] data = new char[size];
        byte[] byData = new byte[size];
        try{
            reader.read(data);     // reads bytes into char[]
            ByteBuffer.wrap(byData).asCharBuffer().put(data);  // converts char[] to byte[]
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
        return byData;
    }

    public void send(byte[] b)
    {
        char[] s = b.toString().toCharArray();
        writer.print(s);
    }

    /**
     * Closes Socket connection and input and output streams
     */
    public void close(){
        try {
            conn.close();
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
