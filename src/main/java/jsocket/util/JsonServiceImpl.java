package jsocket.util;

import com.google.gson.Gson;
import jsocket.exceptions.MalformedJsonException;

/**
 * Default implementation of JsonService. Wraps around com.google.gson.Gson.
 * @author Will Czifro
 * @version 0.1.0
 */
public final class JsonServiceImpl implements JsonService {

    private Gson gson;

    public JsonServiceImpl() {
        gson = new Gson();
    }

    public <T> T fromJson(String json, Class<T> type) {
        try {
            return gson.fromJson(json, type);
        } catch (Throwable throwable) {
            throw new MalformedJsonException(throwable);
        }
    }

    public <T> String toJson(T t, Class<T> type) {
        return gson.toJson(t, type);
    }
}
