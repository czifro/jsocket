package jsocket.datagram;

import jsocket.exceptions.DatagramConnectionException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * @author Will Czifro
 */
public class DatagramImpl implements Datagram {

    private DatagramSocket conn;

    private InetAddress defaultAddress;

    private int defaultPort;

    private int bufferSize;

    private DatagramPacket lastPacket;

    public DatagramImpl(DatagramSocket conn) {
        this(conn, null, -1);
    }

    public DatagramImpl(DatagramSocket conn, InetAddress defaultAddress, int defaultPort) {
        this.conn = conn;
        this.defaultAddress = defaultAddress;
        this.defaultPort = defaultPort;
    }

    public DatagramPacket receivePacket() {
        try {
            DatagramPacket packet = newPacket();
            conn.receive(packet);
            return (lastPacket = packet);
        } catch (IOException e) {
            throw new DatagramConnectionException("Failed to receive packet", e);
        }
    }

    public byte[] receive() {
        return receivePacket().getData();
    }

    public InetAddress getReceivedInetAddress() {
        return lastPacket != null ? lastPacket.getAddress() : null;
    }

    public int getReceivedPort() {
        return lastPacket != null ? lastPacket.getPort() : -1;
    }

    public InetAddress getDefaultInetAddress() {
        return defaultAddress;
    }

    public int getDefaultPort() {
        return defaultPort;
    }

    public void setDefaultInetAddressAndPort(InetAddress address, int port) {
        defaultAddress = address;
        defaultPort = port;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void setTimeout(int timeout) {
        try {
            conn.setSoTimeout(timeout);
        } catch (SocketException e) {
            throw new DatagramConnectionException(e);
        }
    }

    public void send(byte[] data) {
        send(data, defaultAddress, defaultPort);
    }

    public void send(byte[] data, InetAddress address, int port) {
        send(new DatagramPacket(data, data.length, address, port));
    }

    public void send(DatagramPacket packet) {
        try {
            conn.send(packet);
        } catch (IOException e) {
            throw new DatagramConnectionException("Failed to send packet", e);
        }
    }

    public void close() {
        conn.close();
    }

    private DatagramPacket newPacket() {
        byte[] buf = new byte[bufferSize];
        return new DatagramPacket(buf, 0, bufferSize);
    }
}
