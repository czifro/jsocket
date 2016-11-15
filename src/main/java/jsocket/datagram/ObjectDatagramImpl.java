package jsocket.datagram;

import jsocket.util.JsonService;

import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author Will Czifro
 */
public class ObjectDatagramImpl extends StringDatagramImpl implements ObjectDatagram {
    private JsonService jsonService;
    private String jsonString;

    public ObjectDatagramImpl(DatagramSocket conn) {
        super(conn);
    }

    public ObjectDatagramImpl(DatagramSocket conn, InetAddress address, int port) {
        super(conn, address, port);
    }

    /**
     * Receives a complex object, converts byte array to string in JSON format to object
     * @param type type of object
     * @param <T> type of object
     * @return complex object
     */
    public <T> T receiveObject(Class<T> type) {
        jsonString = receiveString();
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
     * Sends complex object, converts complex object to JSON string to byte array
     * @param t generic object
     * @param address address to send to
     * @param port port to send on
     * @param <T> object type
     */
    public <T> void sendObject(T t, InetAddress address, int port) {
        String json = jsonService.toJson(t, (Class<T>) t.getClass());
        sendString(json, address, port);
    }

    /**
     * Sets JSON service for converting to and from JSON string and complex objects
     * @param jsonService JSON service
     */
    public void setJsonService(JsonService jsonService) {
        this.jsonService = jsonService;
    }
}
