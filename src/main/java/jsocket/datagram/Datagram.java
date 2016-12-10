package jsocket.datagram;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * Wraps a {@link java.net.DatagramSocket} and simply sends and receives {@link java.net.DatagramPacket}
 * Base interface of this subpackage
 * Lowest level in abstraction.
 * @author Will Czifro
 * @version 0.1.0
 */
public interface Datagram {

    /**
     * Receives the raw packet
     * @return raw packet
     */
    DatagramPacket receivePacket();

    /**
     * Receives raw packet and returns only the body of the packet
     * @return packet body
     */
    byte[] receive();

    /**
     * Gets the {@link InetAddress} of last received packet
     * @return {@link InetAddress}
     */
    InetAddress getReceivedInetAddress();

    /**
     * Gets the port of last received packet
     * @return port
     */
    int getReceivedPort();

    /**
     * Gets the default {@link InetAddress} that packets are being sent to
     * @return {@link InetAddress}
     */
    InetAddress getDefaultInetAddress();

    /**
     * Gets the default port that packets are being sent on
     * @return port
     */
    int getDefaultPort();

    /**
     * Sets default {@link InetAddress} and port that packets will be sent to.
     * Allows for simple call to {@code send(byte[])}
     * @param address {@link InetAddress} default address
     * @param port int default port
     */
    void setDefaultInetAddressAndPort(InetAddress address, int port);

    /**
     * Sets the default buffer size for receiving data
     * @param bufferSize size of default buffer
     */
    void setBufferSize(int bufferSize);

    /**
     * Allows for a timeout to be set on read ops
     * @param timeout duration to wait for packet
     */
    void setTimeout(int timeout);

    /**
     * This will wrap raw bytes with {@link DatagramPacket}
     * Uses {@link InetAddress} and port from {@code setDefaultInetAddressAndPort(InetAddress,int)}
     * @param data
     */
    void send(byte[] data);

    /**
     * Wraps bytes with {@link DatagramPacket}
     * This will send packet to specified {@link InetAddress} on specified port
     * @param data byte[] data to send
     * @param address {@link InetAddress} address to send to
     * @param port int port to send on
     */
    void send(byte[] data, InetAddress address, int port);

    /**
     * This will simply send the {@link DatagramPacket}
     * It is assumed that the packet is ready to send
     * @param packet
     */
    void send(DatagramPacket packet);

    /**
     * Closes connection.
     */
    void close();
}
