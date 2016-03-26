package jsocket.socket;

import jsocket.util.JsonService;

/**
 * Abstracts jsocket.socket.StringSocket implemntations to handle complex objects instead of strings
 * It is suggested that implementation extend jsocket.socket.StringSocketImpl
 * @author Will Czifro
 * @version 0.1.0
 */
public interface ObjectSocket extends StringSocket {

    /**
     * Receives a complex object, converts byte array to string in JSON format to object
     * @param type type of object
     * @param <T> type of object
     * @return complex object
     */
    <T> T receiveObject(Class<T> type);

    /**
     * Receives a complex object, converts byte array to string in JSON format to object
     * @param type type of object
     * @param <T> type of object
     * @param size size of object
     * @return complex object
     */
    <T> T receiveObject(Class<T> type, int size);

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
     * Sets JSON service for converting to and from JSON string and complex objects
     * @param jsonService JSON service
     */
    void setJsonTool(JsonService jsonService);
}
