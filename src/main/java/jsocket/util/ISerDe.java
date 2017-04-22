package jsocket.util;

/**
 * Generic interface for SerDe servicing
 */
public interface ISerDe {
    <T> byte[] serialize(T t);

    <T> T deserialize(byte[] raw, Class<T> type);
}