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
