package jsocket.socket;

import jsocket.util.FilterFunctionType;

import java.net.*;
import java.util.function.Function;

/**
 * Abstracts jsocket.socket.Socket implementations to handle strings instead of byte arrays
 * It is suggested that implementation extend jsocket.socket.SocketImpl
 * @author Will Czifro
 * @version 0.1.0
 */
public class StringSocketImpl extends SocketImpl implements StringSocket {

    private Function<String, String> filter;
    private boolean useFunc;

    public StringSocketImpl(java.net.Socket conn) {
        super(conn);
    }

    /**
     * Receives data as string, byte array is converted to string
     * @return data
     */
    public String receiveString() {
        return receiveFixedString(getBufferSize());
    }

    /**
     * Receives data as string of certain length, byte array is converted to string
     * @param length length of string
     * @return data
     */
    public String receiveFixedString(int length) {
        byte[] data = receiveAll(length);
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
