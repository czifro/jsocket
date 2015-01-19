/*

    Copyright (C) 2015  Will Czifro

    This file is part of the net.jsock package

    The net.jsock package is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    The net.jsock package is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with the net.jsock package.  If not, see <http://www.gnu.org/licenses/>.

 */

package net.jsock;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by czifro on 12/29/14. A wrapper for Socket connection, can send and receive Strings
 * @author Will Czifro
 * @version 0.1.2
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
     * Sends small String objects
     * @param msg String to be sent
     */
    public void send_msg(String msg){
        send(msg.getBytes());
    }

    /**
     * Sends large String objects
     * @param msg String to be sent
     */
    public void send_large_msg(String msg) {
        int len = msg.getBytes().length;
        send_all(msg.getBytes(), len);
    }
}
