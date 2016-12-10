package jsocket.socket;

import jsocket.exceptions.InstantiationException;
import jsocket.exceptions.SetSocketTimeoutFailureException;
import jsocket.exceptions.SocketStreamException;
import jsocket.exceptions.SocketTimeoutException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketException;

/**
 * Wraps a {@link java.net.Socket} and simply sends and receives bytes
 * Base class for this subpackage
 * Lowest level in abstraction
 * @author Will Czifro
 * @version 0.1.0
 */
public class SocketImpl implements Socket {

    // begin Socket implementation

    //////// Base variables ////////
    private java.net.Socket conn;
    private DataInputStream in;
    private DataOutputStream out;
    private int bufferSize;
    ////////////////////////////////

    public SocketImpl(java.net.Socket conn) {
        try {
            this.conn = conn;
            this.in = new DataInputStream(conn.getInputStream());
            this.out = new DataOutputStream(conn.getOutputStream());
        } catch (Exception e) {
            close();
            throw new InstantiationException(e);
        }
    }

    /**
     * Sets the default buffer size for receiving data
     * @param bufferSize size of default buffer
     */
    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
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
            // We need to check that the exception did not happen due to timeout
            // If it did, we need to wrap it in a runtime exception
            Class<java.net.SocketTimeoutException> steClass = java.net.SocketTimeoutException.class;
            if (e.getClass() == steClass || e.getCause().getClass() == steClass) {
                throw new SocketTimeoutException(e);
            }
            throw new SocketStreamException("Error receiving data", e);
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
            throw new SocketStreamException("Error sending data", e);
        }
    }

    /**
     * Set a timeout on the socket {@code InputStream}.
     * Look to {@link java.net.Socket} for more info
     * @param timeout
     */
    public void setSoTimeout(int timeout) {
        try {
            conn.setSoTimeout(timeout);
        } catch (SocketException e) {
            throw new SetSocketTimeoutFailureException(e);
        }
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
            throw new SocketStreamException("Error when closing connection", e);
        } finally {
            in = null;
            out = null;
            conn = null;
        }
    }

    /**
     * Used by subclasses to access private variable
     * @return the size of the buffer
     */
    protected int getBufferSize() {
        return bufferSize;
    }
    // end Socket implementation
}
