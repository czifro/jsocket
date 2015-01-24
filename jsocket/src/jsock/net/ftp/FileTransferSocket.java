/*

    Copyright (C) 2015  William Czifro

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

import jsock.enums.TransferType;
import jsock.net.MessageSocket;
import jsock.util.ByteChecker;

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
    private Object locker = new Object();

    /**
     * Wraps around a Socket connection and opens I/O streams
     *
     * @param conn A Socket connection
     * @throws IOException Throws IOException if I/O streams cannot be opened
     */
    public FileTransferSocket(Socket conn) throws IOException {
        super(conn);
    }

    /**
     * Receives a file of any type and arbitrary size.
     * An app protocol is used, complements send_file() method
     * Method locks connection to ensure file is received uninterruptedly
     * Method will checksum bytes and send it to transmitter to ensure bytes were accurately received
     * @param folderPath Path to folder file is to be written to
     * @return     File object that points to saved file
     * @throws IOException Throws exception if a file stream cannot be opened, or cannot successfully receive bytes
     */
    public File recv_file(String folderPath) throws IOException {
        synchronized (locker) {
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

            int checksum = 0;
            String msg = "";
            byte[] bytes;

            do{

                bytes = recv_all(buf);

                checksum = ByteChecker.ckecksum(bytes);

                send_msg(Integer.toString(checksum));

                msg = recv_msg();

                if (msg.contains("file transfer failed"))
                {
                    throw new IOException("File failed to come in correctly too many times, aborting...");
                }

            } while (msg.equals("Ok"));

            fos.write(bytes, 0, buf);

            off += buf;
        }

        fos.close();

        return file;
    }

    private File single_stage_recv(File file, int size) throws IOException
    {
        fos = new FileOutputStream(file, false);

        int checksum = 0;
        String msg = "";
        byte[] bytes;

        do{

            bytes = recv_all(size);

            checksum = ByteChecker.ckecksum(bytes);

            send_msg(Integer.toString(checksum));

            msg = recv_msg();

            if (msg.contains("file transfer failed"))
            {
                throw new IOException("File failed to come in correctly too many times, aborting...");
            }

        } while (msg.equals("Ok"));

        fos.write(bytes, 0, size);

        fos.close();

        return file;
    }

    /**
     * File can be of any type and size.
     * Method uses protocol--tells receive how file is being sent,
     *                                         file name, file size.
     * Method locks sending and receiving until file is transferred
     *                                    or exception is thrown.
     * Method checksums bytes it sends and compares it to the checksum
     *                  that the receiver sends back to ensure bytes were
     *                  transferred accurately
     * @param file  File that is to be transferred
     * @throws InvalidParameterException Throws exception if file does not point to a file
     * @throws IOException Throws exception if file cannot be found, or cannot be read, or cannot successfully send bytes
     */
    public void send_file(File file) throws InvalidParameterException, IOException {
        synchronized (locker) {
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

            int checksum = 0, fail_count = 0;
            int recv_checksum = -1;

            do {

                byte[] bytes = new byte[buf];

                checksum = ByteChecker.ckecksum(bytes);

                int available = fis.available();

                fis.read(bytes);

                available = fis.available();

                send_all(bytes, buf);

                recv_checksum = Integer.parseInt(recv_msg());

                if (checksum != recv_checksum) {
                    if (fail_count == 3)
                    {
                        send_msg("Too many mismatches, file transfer failed");
                        throw new IOException("File failed to transfer correctly, aborting...");
                    }

                    send_msg("mismatch, retrying");
                    fail_count++;
                }

            } while (checksum != recv_checksum);

            send_msg("Ok");

            off += len;
        }
    }

    private void single_stage_send(File file) throws IOException
    {
        final int filesize = (int) file.length();


        {   // tells listener how file is being transfer, file name, and file size
            send_msg(TransferType.SINGLESTAGE);
            recv_msg();
            send_msg(file.getName());
            recv_msg();
            send_msg(Long.toString(filesize));
            recv_msg();
        }

        int checksum = 0;
        int recv_checksum = -1;
        int fail_count = 0;

        do {

            byte[] bytes = new byte[filesize];

            checksum = ByteChecker.ckecksum(bytes);

            int available = fis.available();

            fis.read(bytes);

            available = fis.available();

            send_all(bytes, filesize);

            recv_checksum = Integer.parseInt(recv_msg());

            if (checksum != recv_checksum) {
                if (fail_count == 3)
                {
                    send_msg("Too many mismatches, file transfer failed");
                    throw new IOException("File failed to transfer correctly, aborting...");
                }

                send_msg("mismatch, retrying");
                fail_count++;
            }

        } while (checksum != recv_checksum);

        send_msg("Ok");
    }
}
