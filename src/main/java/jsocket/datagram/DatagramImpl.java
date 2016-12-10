package jsocket.datagram;

import jsocket.exceptions.DatagramConnectionException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Wraps a {@link java.net.DatagramSocket} and simply sends and receives {@link java.net.DatagramPacket}
 * Base class of this subpackage
 * Lowest level in abstraction.
 * @author Will Czifro
 * @version 0.1.0
 */
public class DatagramImpl implements Datagram {

    private DatagramSocket conn;

    private InetAddress defaultAddress;

    private int defaultPort;

    private int bufferSize;

    private DatagramPacket lastPacket;

    /**
     * Constructor simply wraps {@link DatagramSocket}
     * @param conn connection to wrap
     */
    public DatagramImpl(DatagramSocket conn) {
        this(conn, null, -1);
    }

    /**
     * Constructor wraps {@link DatagramSocket} and sets
     * default {@link InetAddress} and port
     * @param conn connection to wrap
     * @param defaultAddress default {@link InetAddress} to send to
     * @param defaultPort default port to send on
     */
    public DatagramImpl(DatagramSocket conn, InetAddress defaultAddress, int defaultPort) {
        this.conn = conn;
        this.defaultAddress = defaultAddress;
        this.defaultPort = defaultPort;
    }

    /**
     * Receives the raw packet
     * @return raw packet
     */
    public DatagramPacket receivePacket() {
        try {
            DatagramPacket packet = newPacket();
            conn.receive(packet);
            return (lastPacket = packet);
        } catch (IOException e) {
            throw new DatagramConnectionException("Failed to receive packet", e);
        }
    }

    /**
     * Receives raw packet and returns only the body of the packet
     * @return packet body
     */
    public byte[] receive() {
        return receivePacket().getData();
    }

    /**
     * Gets the {@link InetAddress} of last received packet
     * @return {@link InetAddress}
     */
    public InetAddress getReceivedInetAddress() {
        return lastPacket != null ? lastPacket.getAddress() : null;
    }

    /**
     * Gets the port of last received packet
     * @return port
     */
    public int getReceivedPort() {
        return lastPacket != null ? lastPacket.getPort() : -1;
    }

    /**
     * Gets the default {@link InetAddress} that packets are being sent to
     * @return {@link InetAddress}
     */
    public InetAddress getDefaultInetAddress() {
        return defaultAddress;
    }

    /**
     * Gets the default port that packets are being sent on
     * @return port
     */
    public int getDefaultPort() {
        return defaultPort;
    }

    /**
     * Sets default {@link InetAddress} and port that packets will be sent to.
     * Allows for simple call to {@code send(byte[])}
     * @param address {@link InetAddress} default address
     * @param port int default port
     */
    public void setDefaultInetAddressAndPort(InetAddress address, int port) {
        defaultAddress = address;
        defaultPort = port;
    }

    /**
     * Sets the default buffer size for receiving data
     * @param bufferSize size of default buffer
     */
    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    /**
     * Allows for a timeout to be set on read ops
     * @param timeout duration to wait for packet
     */
    public void setTimeout(int timeout) {
        try {
            conn.setSoTimeout(timeout);
        } catch (SocketException e) {
            throw new DatagramConnectionException(e);
        }
    }

    /**
     * This will wrap raw bytes with {@link DatagramPacket}
     * Uses {@link InetAddress} and port from {@code setDefaultInetAddressAndPort(InetAddress,int)}
     * @param data
     */
    public void send(byte[] data) {
        send(data, defaultAddress, defaultPort);
    }

    /**
     * Wraps bytes with {@link DatagramPacket}
     * This will send packet to specified {@link InetAddress} on specified port
     * @param data byte[] data to send
     * @param address {@link InetAddress} address to send to
     * @param port int port to send on
     */
    public void send(byte[] data, InetAddress address, int port) {
        send(new DatagramPacket(data, data.length, address, port));
    }

    /**
     * This will simply send the {@link DatagramPacket}
     * It is assumed that the packet is ready to send
     * @param packet
     */
    public void send(DatagramPacket packet) {
        try {
            conn.send(packet);
        } catch (IOException e) {
            throw new DatagramConnectionException("Failed to send packet", e);
        }
    }

    /**
     * Closes connection.
     */
    public void close() {
        conn.close();
    }

    private DatagramPacket newPacket() {
        byte[] buf = new byte[bufferSize];
        return new DatagramPacket(buf, 0, bufferSize);
    }
}
