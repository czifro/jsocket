package jsocket.socket;

/**
 * Base interface for this package
 * Lowest level in abstraction
 * @author Will Czifro
 * @version 0.1.0
 */
public interface Socket {

    /**
     * Sets the default buffer size for receiving data
     * @param bufferSize
     */
    void setBufferSize(int bufferSize);

    /**
     * Receives data as byte array
     * @return data
     */
    byte[] receive();

    /**
     * Receives data of certain size as byte array
     * @param size number of bytes to read in, overrides bufferSize for this single method call
     * @return data
     */
    byte[] receiveAll(int size);

    /**
     * Sends data as byte array
     * @param data data to be sent
     */
    void send(byte[] data);

    /**
     * Closes the input and output streams and closes socket connection
     */
    void close();
}
