package net.jsock;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by czifro on 12/29/14. A wrapper for Socket connection, can send and receive Strings
 * @author Will Czifro
 * @version 0.1.1
 */
public class MessageSocket extends JSocket {

    /**
     * Wraps around a Socket connection and opens I/O streams
     * @param conn A Socket connection
     * @throws java.io.IOException Throws IOException if I/O streams cannot be opened
     */
    public MessageSocket(Socket conn) throws IOException {
        super(conn);
    }

    /**
     * Removes '\0' characters from string
     * @param msg message to be rebuilt
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

    /**
     * Receives small message from socket and converts it to a String
     *
     * @return    message received
     */
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

    /**
     * Receives message of specified size from socket and converts it to a String
     *
     * @param size The size to set buffer to
     * @return     message received
     */
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
     * Sends String as byte array
     * @param msg String to be sent
     */
    public void send_msg(String msg){
        send(msg.getBytes());
    }


}
