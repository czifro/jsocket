package jsocket.test.util;

/**
 * @author Will Czifro
 */
@FunctionalInterface
public interface ParamCallable<T,V> {
    V call(T t) throws Exception;
}
