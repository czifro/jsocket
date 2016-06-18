package jsocket.util;

import com.google.gson.Gson;
import jsocket.exceptions.MalformedJsonException;

/**
 * Abstraction so that any JSON library can be used with this package
 * Default implementation of JsonService. Wraps around com.google.gson.Gson.
 * @author Will Czifro
 * @version 0.1.0
 */
public final class JsonServiceImpl implements JsonService {

    private Gson gson;

    public JsonServiceImpl() {
        gson = new Gson();
    }

    /**
     * Converts JSON string to complex object
     * @param json JSON string
     * @param type type of object
     * @param <T> type of object
     * @return complex object
     */
    public <T> T fromJson(String json, Class<T> type) {
        try {
            return gson.fromJson(json, type);
        } catch (Throwable throwable) {
            throw new MalformedJsonException(throwable);
        }
    }

    /**
     * Converts complex object to JSON string
     * @param t generic object
     * @param type type of object
     * @param <T> type of object
     * @return string
     */
    public <T> String toJson(T t, Class<T> type) {
        return gson.toJson(t, type);
    }
}
