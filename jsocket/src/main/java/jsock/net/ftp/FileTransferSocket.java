/*

    Copyright (C) 2015  Czifro Development

    This file is part of the jsock.net.ftp package

    The jsock.net.ftp package is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    The jsock.net.ftp package is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with the jsock.net.ftp package.  If not, see <http://www.gnu.org/licenses/>.

 */

package jsock.net.ftp;

import jsock.net.MessageSocket;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by czifro on 1/17/15. FileTransferSocket allows for files to be sent across a socket connection
 * @author William Czifro
 * @version 0.2.0
 */
public class FileTransferSocket extends MessageSocket {


    /**
     * Wraps around a Socket connection and opens I/O streams
     *
     * @param conn A Socket connection
     * @throws java.io.IOException Throws IOException if I/O streams cannot be opened
     */
    public FileTransferSocket(Socket conn) throws IOException {
        super(conn);
    }
}
