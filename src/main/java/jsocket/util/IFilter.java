package jsocket.util;

/**
 * The functional interface used for applying a filter to a String
 */
@FunctionalInterface
public interface IFilter {
    String filter(String str);
}