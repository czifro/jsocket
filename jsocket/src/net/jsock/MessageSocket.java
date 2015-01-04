package net.jsock;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
* Created by czifro on 12/29/14
* @author Will Czifro
* Handles streaming data to and from client
*/
public class MessageSocket extends JSocket {

    DataOutputStream out;

    /*
    * Creates streams to send and receive data on connection
    * @param Socket,  clients connection
    */
    public MessageSocket(Socket conn) throws IOException {
        super(conn);
        out = new DataOutputStream(conn.getOutputStream());
    }

    /**
     * Removes '\0' characters from string
     * @param msg,  message to be rebuilt
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
     * Writes message to stream and sends it
     * @param msg,  a String to be sent to client
     */
    public void send_msg(String msg){
        send(msg.getBytes());
    }


}
