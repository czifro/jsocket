package jsocket.util;

import com.google.gson.Gson;

/**
 * @author Will Czifro
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
