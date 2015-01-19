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

import java.io.*;
import java.net.Socket;
import java.security.InvalidParameterException;

/**
 * Created by czifro on 1/17/15. FileTransferSocket allows for files to be sent across a socket connection
 * @author William Czifro
 * @version 0.1.0
 */
public class FileTransferSocket extends MessageSocket {

    private FileInputStream fis;
    private FileOutputStream fos;

    /**
     * Wraps around a Socket connection and opens I/O streams
     *
     * @param conn A Socket connection
     * @throws java.io.IOException Throws IOException if I/O streams cannot be opened
     */
    public FileTransferSocket(Socket conn) throws IOException {
        super(conn);
    }

    /**
     * Receives a file of any type and arbitrary size.
     * An app protocol is used, complements send_file() method
     * @param folderPath Path to folder file is to be written to
     * @return     File object that points to saved file
     * @throws IOException Throws exception if a file stream cannot be opened
     */
    public File recv_file(String folderPath) throws IOException
    {
        String transfer_type = recv_msg();
        send_msg("Ok");
        folderPath += recv_msg();
        send_msg("Ok");
        String f_size = recv_msg();
        send_msg("Ok");

        File file = new File(folderPath);

        long size = Long.parseLong(f_size);

        if (transfer_type.equals(TransferType.MULTISTAGE))
            return multi_stage_recv(file, size);
        return single_stage_recv(file, Integer.parseInt(f_size));
    }

    private File multi_stage_recv(File file, long size) throws IOException
    {
        fos = new FileOutputStream(file, false);

        int len = Integer.MAX_VALUE;
        long off = 0;

        while (off < size)
        {
            int diff = (int) (size-off);
            int buf = off + len > size ? diff : len;
            byte[] bytes = recv_all(buf);

            fos.write(bytes, 0, buf);

            off += buf;
        }

        fos.close();

        return file;
    }

    private File single_stage_recv(File file, int size) throws IOException
    {
        fos = new FileOutputStream(file, false);

        byte[] bytes = recv_all(size);

        fos.write(bytes, 0, size);

        fos.close();

        return file;
    }

    /**
     * Takes any type of file format of an arbitrary size.
     * If file is large, file will be sent in chunks.
     * An app protocol is used, sends transfer type--multistage
     * or single stage--file name, and file size before transmitting file
     * @param file  File that is to be transferred
     * @throws InvalidParameterException Throws exception if file does not point to a file
     * @throws IOException Throws exception if file cannot be found, or cannot be read
     */
    public void send_file(File file) throws InvalidParameterException, IOException
    {
        if (!file.exists())
            throw new FileNotFoundException("File does not exist");
        if (!file.isFile())
            throw new InvalidParameterException("Parameter must be a file");

        fis = new FileInputStream(file);

        if (file.length() > Integer.MAX_VALUE)
            multi_stage_send(file);
        else
            single_stage_send(file);

        fis.close();
    }

    private void multi_stage_send(File file) throws IOException
    {
        final long filesize = file.length();
        final int len = Integer.MAX_VALUE;
        long off = 0;


        {   // tells listener how file is being transferred, file name, and file size
            send_msg(TransferType.MULTISTAGE);
            recv_msg();
            send_msg(file.getName());
            recv_msg();
            send_msg(Long.toString(filesize));
            recv_msg();
        }

        while (off < filesize)
        {
            int diff = (int) (filesize-off);
            int buf = (off+len > filesize ? diff : len);
            byte[] bytes = new byte[buf];

            fis.read(bytes, 0, buf);

            send_all(bytes, buf);

            off += len;
        }
    }

    private void single_stage_send(File file) throws IOException
    {
        final int filesize = (int) file.length();

        fis = new FileInputStream(file);

        {   // tells listener how file is being transfer, file name, and file size
            send_msg(TransferType.SINGLESTAGE);
            recv_msg();
            send_msg(file.getName());
            recv_msg();
            send_msg(Long.toString(filesize));
            recv_msg();
        }

        byte[] bytes = new byte[filesize];

        int available = fis.available();

        fis.read(bytes);

        available = fis.available();

        send_all(bytes, filesize);
    }



    private static class TransferType {
        public final static String MULTISTAGE = "MULTISTAGE";
        public final static String SINGLESTAGE = "SINGLESTAGE";
    }
}
