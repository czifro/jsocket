package jsocket.datagram;

import jsocket.util.FilterFunctionType;

import java.net.InetAddress;
import java.util.function.Function;

/**
 * @author Will Czifro
 */
public interface StringDatagram extends Datagram {
    /**
     * Receives data as string, byte array is converted to string
     * @return data
     */
    String receiveString();

    /**
     * Sends a string, string will be converted to byte array then sent
     * @param str string to be sent
     */
    void sendString(String str);

    /**
     * Sends a string, string will be converted to byte array then sent
     * @param str string to be sent
     * @param address address to send to
     * @param port port to send on
     */
    void sendString(String str, InetAddress address, int port);

    /**
     * A function can be used to process string before returning it
     * in some cases, null characters are mixed in a string
     * A filter function can be used to remove null characters
     * @param type type of filter function
     */
    void setFilterFunction(FilterFunctionType type);

    /**
     * A function can be used to process string before returning it
     * in some cases, null characters are mixed in a string
     * A filter function can be used to remove null characters
     * @param func filter function
     */
    void setFilterFunction(Function<String, String> func);

    /**
     * Toggles whether or not a filter function will be applied to received strings
     * @param useFunc toggle
     */
    void useFilterFunction(boolean useFunc);
}
