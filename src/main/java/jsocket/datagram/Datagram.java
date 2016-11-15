package jsocket.datagram;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 *
 * @author Will Czifro
 * @version 0.1.0
 */
public interface Datagram {

    DatagramPacket receivePacket();

    byte[] receive();

    InetAddress getReceivedInetAddress();

    int getReceivedPort();

    InetAddress getDefaultInetAddress();

    int getDefaultPort();

    void setDefaultInetAddressAndPort(InetAddress address, int port);

    /**
     * Sets the default buffer size for receiving data
     * @param bufferSize size of default buffer
     */
    void setBufferSize(int bufferSize);

    void setTimeout(int timeout);

    void send(byte[] data);

    void send(byte[] data, InetAddress address, int port);

    void send(DatagramPacket packet);

    void close();
}
