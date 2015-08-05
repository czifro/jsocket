package jsock.net;

import jsock.crypto.RSA;
import jsock.interfaces.IEncryptSocket;
import jsock.util.ByteTool;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by czifro on 12/29/14. A wrapper for Socket connection
 * @author Will Czifro
 * @version 0.3.1
 */
public class JSocket implements IEncryptSocket {

    protected DataOutputStream out;
    protected DataInputStream in;
    protected Socket conn;
    protected RSA rsa;

    private Object jLocker = new Object();

    /**
     * Default size is 96, increase to read and write larger chunks
     */
    public int CHUNK_SIZE = 96;

    /**
     * Wraps around a Socket connection and opens I/O streams
     * @param conn A Socket connection
     * @throws java.io.IOException Throws IOException if I/O streams cannot be opened
     */
    public JSocket(Socket conn) throws IOException {
        this.conn = conn;
        out = new DataOutputStream(conn.getOutputStream());
        in = new DataInputStream(conn.getInputStream());
    }

    /**
     * Receives small message in bytes
     *
     * @return   bytes received
     */
    public byte[] recv(){
        byte[] bytes = new byte[1024];
        try {
            in.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * Receives message of specified size in bytes
     *
     * @param size buffer size
     * @return     bytes received
     */
    public byte[] recv_all(int size)
    {
        byte[] bytes = new byte[size];
        int off = 0;
        try {
            in.read(bytes, off, size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * Sends bytes
     *
     * @param b bytes to be sent
     */
    public void send(byte[] b)
    {
        try {
            out.write(b);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes Socket connection and input and output streams
     */
    public void close(){
        try {
            conn.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if connection has been closed
     *
     * @return    True if socket is closed, otherwise false
     */
    public boolean isClosed()
    {
        return conn.isClosed();
    }

    public static void main(String [] args)
    {}

    @Override
    public void encryptConnection(RSA rsa) {
        this.rsa = rsa;
    }

    @Override
    public boolean connectionIsEncrypted() {
        return rsa != null;
    }

    @Override
    public byte[] recv_encrypted() throws Exception
    {
        synchronized (jLocker)
        {
            try {
                byte[] b_len = recv_all(4);

                send(b_len);

                int len = ByteTool.byteArrayToInt(b_len);

                byte[] bytes = recv_all(len);

                bytes = rsa.decrypt(bytes);

                return bytes;
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
            throw new Exception("Failed to receive encrypted bytes");
        }
    }

    @Override
    public void send_encrypted(byte[] bytes) {
        synchronized (jLocker)
        {
            try {
                bytes = rsa.encrypt(bytes);

                byte[] len = ByteTool.intToByteArray(bytes.length, 4);

                send(len);

                recv();

                send(bytes);

            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
    }

}
