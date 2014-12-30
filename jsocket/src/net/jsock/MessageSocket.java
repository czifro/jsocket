package net.jsock;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
* Created by czifro on 12/29/14
* @author Will Czifro
* Handles streaming data to and from client
*/
public class MessageSocket extends JSocket {

    /*
    * Creates streams to send and receive data on connection
    * @param Socket,  clients connection
    */
    public MessageSocket(Socket conn) throws IOException {
        super(conn);
    }

    /*
    * Removes '\0' characters from string
    * @param String,  message to be rebuilt
    * @return String,  rebuilt message
    */
    private static String constructCleanMessage(String msg){
        String temp = "";
        for (char c : msg.toCharArray()){
            if (c == '\0'){
                continue;
            }
            temp += c;
        }

        return temp;
    }

    public String recv_msg()
    {
        String msg = "";
        byte[] bytes = recv();
        try {
            msg = constructCleanMessage(new String(bytes, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public String recv_all_msg(int size)
    {
        String msg = "";
        byte[] bytes = recv_all(size);
        try {
            msg = constructCleanMessage(new String(bytes, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * Receives message from client in bytes
     *
     * @return byte[],  bytes sent by client
     */
    @Override
    protected byte[] recv(){
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

    @Override
    protected byte[] recv_all(int size)
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

    /**
     * Writes message to stream and sends it
     * @param msg,  a String to be sent to client
     */
    public void send_msg(String msg){
        send(msg.toCharArray());
    }

    @Override
    protected void send(char[] s)
    {
        writer.print(s);
    }

    /*
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
