package jsocket.datagram;

import jsocket.util.FilterFunctionType;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.function.Function;

/**
 * @author Will Czifro
 */
public class StringDatagramImpl extends DatagramImpl implements StringDatagram {

    private Function<String, String> filter;
    private boolean useFunc;

    public StringDatagramImpl(DatagramSocket conn) {
        super(conn);
    }

    public StringDatagramImpl(DatagramSocket conn, InetAddress address, int port) {
        super(conn, address, port);
    }

    /**
     * Receives data as string, byte array is converted to string
     * @return data
     */
    public String receiveString() {
        byte[] data = receive();
        return buildString(data);
    }

    /**
     * Sends a string, string will be converted to byte array then sent
     * @param str string to be sent
     */
    public void sendString(String str) {
        send(str.getBytes());
    }

    /**
     * Sends a string, string will be converted to byte array then sent
     * @param str string to be sent
     * @param address address to send to
     * @param port port to send on
     */
    public void sendString(String str, InetAddress address, int port) {
        send(str.getBytes(), address, port);
    }

    /**
     * A function can be used to process string before returning it
     * in some cases, null characters are mixed in a string
     * A filter function can be used to remove null characters
     * @param type type of filter function
     */
    public void setFilterFunction(FilterFunctionType type) {
        filter = type.getFunc();
    }

    /**
     * A function can be used to process string before returning it
     * in some cases, null characters are mixed in a string
     * A filter function can be used to remove null characters
     * @param func filter function
     */
    public void setFilterFunction(Function<String, String> func) {
        filter = func;
    }

    /**
     * Toggles whether or not a filter function will be applied to received strings
     * @param useFunc toggle
     */
    public void useFilterFunction(boolean useFunc) {
        this.useFunc = useFunc;
    }

    /**
     * Converts a byte array to a string
     * @param data the byte array to be converted to a string
     * @return a string
     */
    private String buildString(byte[] data) {
        String msg = new String(data);
        return runFilterOnString(msg);
    }

    /**
     * Uses the set filter and processes the string
     * If the useFunc flag is not set, the string is just returned
     * @param str the string to be processed
     * @return the processed string
     */
    private String runFilterOnString(String str) {
        if (!useFunc) return str;
        return filter.apply(str);
    }
}
