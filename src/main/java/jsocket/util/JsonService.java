package jsocket.util;

/**
 * Abstraction so that any JSON library can be used with this package
 * @author Will Czifro
 * @version 0.1.0
 */
public interface JsonService {

    /**
     * Converts JSON string to complex object
     * @param json JSON string
     * @param type type of object
     * @param <T> type of object
     * @return complex object
     */
    <T> T fromJson(String json, Class<T> type);

    /**
     * Converts complex object to JSON string
     * @param t generic object
     * @param type type of object
     * @param <T> type of object
     * @return string
     */
    <T> String toJson(T t, Class<T> type);

    /**
     * Creates a new instance of JsonService using the default implementation
     * @return new JsonService instance
     */
    static JsonService defaultImpl() {
        return new JsonServiceImpl();
    }
}
