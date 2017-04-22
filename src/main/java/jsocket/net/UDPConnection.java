package jsocket.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Abstraction used for UDP based connections
 * @author Will Czifro
 */
public class UDPConnection extends Connection {

    private InetAddress defaultAddress;
    private int defaultPort;
    private DatagramPacket lastPacket;

    /**
     * @param conn raw socket
     */
    public UDPConnection(DatagramSocket conn) {
        super(conn);
    }

    /**
     * This is more useful for client side
     * @param conn raw socket
     * @param address default address to send to
     * @param port default port to send to
     */
    public UDPConnection(DatagramSocket conn, InetAddress address, int port) {
        this(conn);
        defaultAddress = address;
        defaultPort = port;
    }

    /**
     * This is a no-op for UDP
     */
    protected void init() {}

    /**
     * Helper to reduce key strokes.
     * @return
     */
    protected DatagramSocket sock() {
        return (DatagramSocket) conn;
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
     * Receives the raw packet
     * @return raw packet
     */
    public DatagramPacket receivePacket() {
        try {
            DatagramPacket packet = newPacket();
            sock().receive(packet);
            return (lastPacket = packet);
        } catch (IOException e) {
            throwIfSocketTimedOut(e);
            throw new ConnectionException("Failed to receive packet", e);
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
     * This will wrap raw bytes with {@link DatagramPacket}
     * Uses {@link InetAddress} and port from {@code setDefaultInetAddressAndPort(InetAddress,int)}
     * @param data
     * @throws {@link ConnectionException} thrown when data exceeds packet size or when packet failed to send
     */
    public void send(byte[] data) {
        send(data, defaultAddress, defaultPort);
    }

    /**
     * Wraps bytes with {@link DatagramPacket}
     * This will send packet to specified {@link InetAddress} to specified port
     * @param data byte[] data to send
     * @param address {@link InetAddress} address to send to
     * @param port int port to send to
     * @throws {@link ConnectionException} thrown when data exceeds packet size or when packet failed to send
     */
    public void send(byte[] data, InetAddress address, int port) {
        if (data.length > 512) throw new ConnectionException("Exceeded packet size");
        send(new DatagramPacket(data, data.length, address, port));
    }

    /**
     * This will simply send the {@link DatagramPacket}
     * It is assumed that the packet is ready to send
     * @param packet
     * @throws {@link ConnectionException} thrown when packet fails to send
     */
    public void send(DatagramPacket packet) {
        try {
            sock().send(packet);
        } catch (IOException e) {
            throw new ConnectionException("Failed to send packet", e);
        }
    }

    /**
     * no-op for UDP
     */
    public void setBufferSize(int bufferSize) {}

    private DatagramPacket newPacket() {
        byte[] buf = new byte[512];
        return new DatagramPacket(buf, 0, 512);
    }
}
