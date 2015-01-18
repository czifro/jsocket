package net.jsock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidParameterException;
import java.util.Scanner;

/**
 * Created by czifro on 1/17/15. FileTransferSocket allows for files to be sent across a socket connection
 * @author William Czifro
 * @version 0.1.0
 */
public class FileTransferSocket extends MessageSocket {

    private Scanner input;
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
     * @param path Path to folder file is to be written to
     * @return     File object that points to saved file
     * @throws IOException Throws exception if a file stream cannot be opened
     */
    public File recv_file(String path) throws IOException
    {
        String transfer_type = recv_msg();
        send_msg("Ok");
        path += recv_msg();
        send_msg("Ok");
        String f_size = recv_msg();
        send_msg("Ok");

        File file = new File(path);

        if (transfer_type.equals(TransferType.MULTISTAGE))
            return multi_stage_recv(file, Long.getLong(f_size));
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
     * @throws FileNotFoundException Throws exception if file cannot be found
     */
    public void send_file(File file) throws InvalidParameterException, FileNotFoundException
    {
        if (!file.exists())
            throw new FileNotFoundException("File does not exist");
        if (!file.isFile())
            throw new InvalidParameterException("Parameter must be a file");


        if (file.length() > Integer.MAX_VALUE)
            multi_stage_send(file);
        else
            single_stage_send(file);

        input.close();
    }

    private void multi_stage_send(File file) throws FileNotFoundException
    {
        final long filesize = file.length();
        final int len = Integer.MAX_VALUE;
        long off = 0;

        input = new Scanner(file);

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

            for (int i = 0; i < buf; ++i)
            {
                bytes[i] = input.nextByte();
            }

            send_all(bytes, buf);

            off += len;
        }
    }

    private void single_stage_send(File file) throws FileNotFoundException
    {
        final int filesize = (int) file.length();

        input = new Scanner(file);

        {   // tells listener how file is being transfer, file name, and file size
            send_msg(TransferType.SINGLESTAGE);
            recv_msg();
            send_msg(file.getName());
            recv_msg();
            send_msg(Long.toString(filesize));
            recv_msg();
        }

        byte[] bytes = new byte[filesize];

        for (int i = 0; i < filesize; ++i)
        {
            bytes[i] = input.nextByte();
        }

        send_all(bytes, filesize);
    }



    private static class TransferType {
        public final static String MULTISTAGE = "MULTISTAGE";
        public final static String SINGLESTAGE = "SINGLESTAGE";
    }
}
