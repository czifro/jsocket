/*

    Copyright (C) 2015  Czifro Development

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

import jsock.enums.StringToolType;
import jsock.util.StringTool;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by czifro on 12/29/14. A wrapper for Socket connection, can send and receive Strings
 * @author Will Czifro
 * @version 0.2.0
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
     * Receives small message from socket and converts it to a String
     *
     * @return    message received
     */
    public String recv_msg()
    {
        String msg = "";
        byte[] bytes = new byte[0];

        if (connectionIsEncrypted())
        {
            try {
                bytes = recv_encrypted();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
            bytes = recv();
        try {
            msg = StringTool.cleanString(new String(bytes, "UTF-8"), StringToolType.ONLY_NULLS);
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
    public String recv_large_msg(int size)
    {
        String msg = "";
        byte[] bytes = recv_all(size);

        if (connectionIsEncrypted())
        {
            try {
                bytes = rsa.decrypt(bytes);
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        try {
            msg = StringTool.cleanString(new String(bytes, "UTF-8"), StringToolType.ONLY_NULLS);
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
        if (connectionIsEncrypted())
        {
            send_encrypted(msg.getBytes());
            return;
        }
        send(msg.getBytes());
    }

}
