package jsocket.socket;

import jsocket.util.JsonService;

import java.net.*;

/**
 * @author Will Czifro
 */
public class ObjectSocketImpl extends StringSocketImpl implements ObjectSocket {

    private JsonService jsonService;
    private String jsonString;

    public ObjectSocketImpl(java.net.Socket conn) {
        super(conn);
    }

    public <T> T receiveObject(Class<T> type) {
        return receiveObject(type, getBufferSize());
    }

    public <T> T receiveObject(Class<T> type, int size) {
        jsonString = receiveFixedString(size);
        return jsonService.fromJson(jsonString, type);
    }

    public String getMalformedJson() {
        return jsonString;
    }

    public <T> void sendObject(T t) {
        String json = jsonService.toJson(t, (Class<T>) t.getClass());
        sendString(json);
    }

    public void setJsonTool(JsonService jsonService) {
        this.jsonService = jsonService;
    }
}
