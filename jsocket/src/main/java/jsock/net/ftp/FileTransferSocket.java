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

import jsock.enums.AckType;
import jsock.enums.TransferType;
import jsock.net.MessageSocket;
import jsock.util.ByteTool;

import java.io.*;
import java.net.Socket;
import java.security.InvalidParameterException;

/**
 * Created by czifro on 1/17/15. FileTransferSocket allows for files to be sent across a socket connection
 * @author William Czifro
 * @version 0.2.0
 */
public class FileTransferSocket extends MessageSocket {

    private FileInputStream fis;
    private FileOutputStream fos;
    private Object locker = new Object();

    private final int LONG_BYTE_ARRAY_SIZE = 8;
    private final int INT_BYTE_ARRAY_SIZE = 4;
    private final int ACK_BYTE_ARRAY_SIZE = 4;

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
     * Method locks connection to ensure file is received uninterruptedly
     * Method will checksum bytes and send it to transmitter to ensure bytes were accurately received
     * @param folderPath Path to folder file is to be written to
     * @return     File object that points to saved file
     * @throws java.io.IOException Throws exception if a file stream cannot be opened, or cannot successfully receive bytes
     */
    public File recv_file(String folderPath) throws IOException {
        synchronized (locker) {
            String transfer_type = recv_msg();
            send(ByteTool.intToByteArray(AckType.ACK, ACK_BYTE_ARRAY_SIZE));
            folderPath += recv_msg();
            send(ByteTool.intToByteArray(AckType.ACK, ACK_BYTE_ARRAY_SIZE));
            long size = ByteTool.byteArrayToLong(recv_all(LONG_BYTE_ARRAY_SIZE));
            send(ByteTool.intToByteArray(AckType.ACK, ACK_BYTE_ARRAY_SIZE));

            File file = new File(folderPath);

            if (transfer_type.equals(TransferType.MULTISTAGE))
                return multi_stage_recv(file, size, false);
            return single_stage_recv(file, (int) size, false);
        }
    }

    public File recv_file_encrypted(String folderPath) throws IOException {
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
                return multi_stage_recv(file, size, true);
            return single_stage_recv(file, Integer.parseInt(f_size), true);
        }
    }

    private File multi_stage_recv(File file, long size, boolean decrypt) throws IOException
    {
        fos = new FileOutputStream(file, false);

        int len = 1024000;
        long off = 0;

        while (off < size)
        {
            int diff = (int) (size-off);
            int buf = off + len > size ? diff : len;

            int checksum = 0, ack = 0;
            byte[] bytes = new byte[0];

            do{

                if (ByteTool.byteArrayToInt(recv_all(ACK_BYTE_ARRAY_SIZE)) == AckType.PREPARE) {
                    send(ByteTool.intToByteArray(AckType.ACK, ACK_BYTE_ARRAY_SIZE));
                    if (ByteTool.byteArrayToInt(recv_all(ACK_BYTE_ARRAY_SIZE)) != AckType.ACK)
                        continue;
                    System.out.println("Ready to receive...");
                }
                else
                    continue;

                if (decrypt && rsa != null)
                {
                    try {
                        bytes = recv_encrypted();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                    bytes = recv_all(buf);

                checksum = ByteTool.ckecksum(bytes);

                System.out.println("Receiver's checksum: " + checksum);

                send(ByteTool.intToByteArray(checksum, INT_BYTE_ARRAY_SIZE));

                ack = ByteTool.byteArrayToInt(recv_all(ACK_BYTE_ARRAY_SIZE));

                if (ack == AckType.ABORT)
                {
                    throw new IOException("File failed to come in correctly too many times, aborting...");
                }

            } while (ack == AckType.RETRY);

            fos.write(bytes, 0, buf);

            off += buf;
        }

        fos.close();

        return file;
    }

    private File single_stage_recv(File file, int size, boolean decrypt) throws IOException
    {
        fos = new FileOutputStream(file, false);

        int checksum = 0, ack = 0;
        String msg = "";
        byte[] bytes = new byte[0];

        do{

            if (decrypt && rsa != null)
            {
                try {
                    bytes = recv_encrypted();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
                bytes = recv_all(size);

            checksum = ByteTool.ckecksum(bytes);

            send(ByteTool.intToByteArray(checksum, INT_BYTE_ARRAY_SIZE));

            ack = ByteTool.byteArrayToInt(recv_all(ACK_BYTE_ARRAY_SIZE));

            if (ack == AckType.ABORT)
            {
                throw new IOException("File failed to come in correctly too many times, aborting...");
            }

        } while (ack == AckType.RETRY);

        fos.write(bytes, 0, size);

        fos.close();

        return file;
    }

    /**
     * Sends a file over a socket connection
     * Method uses protocol--tells receive how file is being sent,
     *                                         file name, file size.
     * Method locks sending and receiving until file is transferred
     *                                    or exception is thrown.
     * Method checksums bytes it sends and compares it to the checksum
     *                  that the receiver sends back to ensure bytes were
     *                  transferred accurately
     * @param file  File that is to be transferred, arbitrary size, any file type
     * @throws java.security.InvalidParameterException Throws exception if file does not point to a file
     * @throws java.io.IOException Throws exception if file cannot be found, or cannot be read, or cannot successfully send bytes
     */
    public void send_file(File file) throws InvalidParameterException, IOException {
        synchronized (locker) {
            if (!file.exists())
                throw new FileNotFoundException("File does not exist");
            if (!file.isFile())
                throw new InvalidParameterException("Parameter must be a file");

            fis = new FileInputStream(file);

            long freeMemory = Runtime.getRuntime().freeMemory();
            long fileLength = file.length();

            if (fileLength > Integer.MAX_VALUE || fileLength > freeMemory)
                multi_stage_send(file, false);
            else
                single_stage_send(file, false);

            fis.close();
        }
    }

    /**
     * Encrypts and sends file over a socket connection
     * Functions the same as send_file(file)
     * @param file  File that is to be trasferred, arbitrary size, any file type
     * @throws java.security.InvalidParameterException Throws exception if file does not point to a file
     * @throws java.io.IOException Throws exception if file cannot be found, or cannot be read, or cannot successfully send bytes
     */
    public void send_file_encrypted(File file) throws InvalidParameterException, IOException {
        synchronized (locker) {
            if (!file.exists())
                throw new FileNotFoundException("File does not exist");
            if (!file.isFile())
                throw new InvalidParameterException("Parameter must be a file");

            fis = new FileInputStream(file);

            long freeMemory = Runtime.getRuntime().freeMemory();
            long fileLength = file.length();

            if (fileLength > Integer.MAX_VALUE || fileLength > freeMemory)
                multi_stage_send(file, true);
            else
                single_stage_send(file, true);

            fis.close();
        }
    }

    private void multi_stage_send(File file, boolean encrypt) throws IOException
    {
        final long filesize = file.length();
        final int len = 1024000;
        long off = 0;


        {   // tells listener how file is being transferred, file name, and file size
            send_msg(TransferType.MULTISTAGE);
            recv_all(ACK_BYTE_ARRAY_SIZE);
            send_msg(file.getName());
            recv_all(ACK_BYTE_ARRAY_SIZE);
            send(ByteTool.longToByteArray(filesize, LONG_BYTE_ARRAY_SIZE));
            recv_all(ACK_BYTE_ARRAY_SIZE);
        }

        while (off < filesize)
        {
            int diff = (int) (filesize-off);
            int buf = (off+len > filesize ? diff : len);

            int checksum = 0, fail_count = 0;
            int recv_checksum = -1;

            byte[] bytes = new byte[buf];

            int available = fis.available();

            bytes = getBytesFromFile(bytes, buf);

            available = fis.available();

            checksum = ByteTool.ckecksum(bytes);

            System.out.println("Sender's checksum:   " + checksum);

            do {
                send(ByteTool.intToByteArray(AckType.PREPARE, ACK_BYTE_ARRAY_SIZE));

                if (ByteTool.byteArrayToInt(recv_all(ACK_BYTE_ARRAY_SIZE)) != AckType.ACK)
                    continue;
                else {
                    send(ByteTool.intToByteArray(AckType.ACK, ACK_BYTE_ARRAY_SIZE));
                    System.out.println("Ready to send...");
                }

                if (encrypt && rsa != null)
                    send_encrypted(bytes);
                else
                    send(bytes);

                recv_checksum = ByteTool.byteArrayToInt(recv_all(INT_BYTE_ARRAY_SIZE));

                if (checksum != recv_checksum) {
                    if (fail_count == 3)
                    {
                        send(ByteTool.intToByteArray(AckType.ABORT, ACK_BYTE_ARRAY_SIZE));
                        throw new IOException("File failed to transfer correctly, aborting...");
                    }

                    send(ByteTool.intToByteArray(AckType.RETRY, ACK_BYTE_ARRAY_SIZE));
                    fail_count++;
                }

            } while (checksum != recv_checksum);

            send(ByteTool.intToByteArray(AckType.CONTINUE, ACK_BYTE_ARRAY_SIZE));

            off += len;
        }
    }

    private void single_stage_send(File file, boolean encrypt) throws IOException
    {
        final int filesize = (int) file.length();


        {   // tells listener how file is being transfer, file name, and file size
            send_msg(TransferType.SINGLESTAGE);
            recv_msg();
            send_msg(file.getName());
            recv_msg();
            send(ByteTool.longToByteArray(filesize, LONG_BYTE_ARRAY_SIZE));
            recv_msg();
        }

        int checksum = 0;
        int recv_checksum = -1;
        int fail_count = 0;

        byte[] bytes = new byte[filesize];

        int available = fis.available();

        fis.read(bytes);

        available = fis.available();

        checksum = ByteTool.ckecksum(bytes);

        do {
            if (encrypt && rsa != null)
                send_encrypted(bytes);
            else
                send(bytes);

            recv_checksum = ByteTool.byteArrayToInt(recv_all(INT_BYTE_ARRAY_SIZE));

            if (checksum != recv_checksum) {
                if (fail_count == 3)
                {
                    send(ByteTool.intToByteArray(AckType.ABORT, ACK_BYTE_ARRAY_SIZE));
                    throw new IOException("File failed to transfer correctly, aborting...");
                }

                send(ByteTool.intToByteArray(AckType.RETRY, ACK_BYTE_ARRAY_SIZE));
                fail_count++;
            }

        } while (checksum != recv_checksum);

        send(ByteTool.intToByteArray(AckType.CONTINUE, ACK_BYTE_ARRAY_SIZE));
    }

    private byte[] getBytesFromFile(byte[] bytes, int len) throws IOException {
        for (int i = 0; i < len; ++i)
        {
            bytes[i] = (byte) fis.read();
        }

        return bytes;
    }
}
