package jsocket.datagram;

import jsocket.util.JsonService;

import java.net.InetAddress;

/**
 * @author Will Czifro
 */
public interface ObjectDatagram extends StringDatagram {
    /**
     * Receives a complex object, converts byte array to string in JSON format to object
     * @param type type of object
     * @param <T> type of object
     * @return complex object
     */
    <T> T receiveObject(Class<T> type);

    /**
     * If receiving object fails, this method can recover malformed JSON string
     * @return JSON string
     */
    String getMalformedJson();

    /**
     * Sends complex object, converts complex object to JSON string to byte array
     * @param t generic object
     * @param <T> object type
     */
    <T> void sendObject(T t);

    /**
     * Sends complex object, converts complex object to JSON string to byte array
     * @param t generic object
     * @param address address to send to
     * @param port port to send on
     * @param <T> object type
     */
    <T> void sendObject(T t, InetAddress address, int port);

    /**
     * Sets JSON service for converting to and from JSON string and complex objects
     * @param jsonService JSON service
     */
    void setJsonService(JsonService jsonService);
}
