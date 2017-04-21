package jsocket.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Will Czifro
 */
public class TCPConnection extends Connection {

    private DataInputStream in;
    private DataOutputStream out;
    private int bufferSize;

    public TCPConnection(Socket conn) {
        super(conn);
    }

    /**
     * TCP connection needs to be initialized.
     * Grab streams from {@link java.net.Socket}
     */
    protected void init() {
        try {
            this.in = new DataInputStream(sock().getInputStream());
            this.out = new DataOutputStream(sock().getOutputStream());
        } catch (Exception e) {
            throw new ConnectionException("Failed to initialize TCP connection", e);
        } finally {
            close();
        }
    }

    /**
     * Helper to reduce key strokes.
     * @return
     */
    protected Socket sock() {
        return (Socket) conn;
    }

    /**
     * Receives data as byte array
     * @return data
     */
    public byte[] receive() {
        return receiveAll(bufferSize);
    }

    /**
     * Receives data of certain size as byte array
     * @param size number of bytes to read in, overrides bufferSize for this single method call
     * @return data
     */
    public byte[] receiveAll(int size) {
        try {
            byte[] data = new byte[size];
            in.read(data, 0, size);
            return data;
        } catch (Exception e) {
            throwIfSocketTimedOut(e);
            throw new ConnectionException("Error receiving data", e);
        }
    }

    /**
     * Sends data as byte array
     * @param data data to be sent
     */
    public void send(byte[] data) {
        try {
            out.write(data, 0, data.length);
        } catch (Exception e) {
            throw new ConnectionException("Error sending data", e);
        }
    }

    /**
     * Sets the buffer size for underlying implementation.
     * @param bufferSize number of bytes to allocate for buffer.
     */
    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    /**
     * Closes the input and output streams and closes socket connection
     */
    public void close() {
        try {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
            if (conn != null)
                conn.close();
        } catch (IOException e) {
            throw new ConnectionException("Error when closing connection", e);
        } finally {
            in = null;
            out = null;
            conn = null;
        }
    }
}
