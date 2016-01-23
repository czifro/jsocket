package jsocket.socket;

import jsocket.util.FilterFunctionType;

import java.util.function.Function;

/**
 * Abstracts jsocket.socket.Socket implementations to handle strings instead of byte arrays
 * It is suggested that implementation extend jsocket.socket.SocketImpl
 * @author Will Czifro
 * @version 0.1.0
 */
public interface StringSocket {

    /**
     * Receives data as string, byte array is converted to string
     * @return data
     */
    String receiveString();

    /**
     * Receives data as string of certain length, byte array is converted to string
     * @param length length of string
     * @return data
     */
    String receiveFixedString(int length);

    /**
     * Sends a string, string will be converted to byte array then sent
     * @param str string to be sent
     */
    void sendString(String str);

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
