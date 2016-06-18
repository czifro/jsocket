package jsocket.socket;

import jsocket.util.JsonService;

import java.net.*;

/**
 * Abstracts jsocket.socket.StringSocket implemntations to handle complex objects instead of strings
 * It is suggested that implementation extend jsocket.socket.StringSocketImpl
 * Default implementation
 * @author Will Czifro
 * @version 0.1.0
 */
public class ObjectSocketImpl extends StringSocketImpl implements ObjectSocket {

    private JsonService jsonService;
    private String jsonString;

    public ObjectSocketImpl(java.net.Socket conn) {
        super(conn);
    }

    /**
     * Receives a complex object, converts byte array to string in JSON format to object
     * @param type type of object
     * @param <T> type of object
     * @return complex object
     */
    public <T> T receiveObject(Class<T> type) {
        return receiveObject(type, getBufferSize());
    }

    /**
     * Receives a complex object, converts byte array to string in JSON format to object
     * @param type type of object
     * @param <T> type of object
     * @param size size of object
     * @return complex object
     */
    public <T> T receiveObject(Class<T> type, int size) {
        jsonString = receiveFixedString(size);
        return jsonService.fromJson(jsonString, type);
    }

    /**
     * If receiving object fails, this method can recover malformed JSON string
     * @return JSON string
     */
    public String getMalformedJson() {
        return jsonString;
    }

    /**
     * Sends complex object, converts complex object to JSON string to byte array
     * @param t generic object
     * @param <T> object type
     */
    public <T> void sendObject(T t) {
        String json = jsonService.toJson(t, (Class<T>) t.getClass());
        sendString(json);
    }

    /**
     * Sets JSON service for converting to and from JSON string and complex objects
     * @param jsonService JSON service
     */
    public void setJsonService(JsonService jsonService) {
        this.jsonService = jsonService;
    }
}
