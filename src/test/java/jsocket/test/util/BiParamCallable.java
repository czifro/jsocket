package jsocket.test.util;

/**
 * @author Will Czifro
 */
@FunctionalInterface
public interface BiParamCallable<T,V,Z> {
    Z call(T t, V v) throws Exception;
}
