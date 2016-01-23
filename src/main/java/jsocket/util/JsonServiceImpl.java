package jsocket.util;

import com.google.gson.Gson;

/**
 * Default implementation of JsonService. Wraps around com.google.gson.Gson.
 * @author Will Czifro
 * @version 0.1.0
 */
public class JsonServiceImpl implements JsonService {

    private Gson gson;

    public JsonServiceImpl() {
        gson = new Gson();
    }

    public <T> T fromJson(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }

    public <T> String toJson(T t, Class<T> type) {
        return gson.toJson(t, type);
    }
}
