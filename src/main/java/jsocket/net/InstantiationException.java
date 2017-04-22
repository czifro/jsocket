package jsocket.net;

/**
 * Thrown if an exception occurs during constructor call
 * @author Will Czifro
 * @version 0.1.0
 */
public class InstantiationException extends RuntimeException {

    public InstantiationException(String message) {
        super(message);
    }

    public InstantiationException(Throwable throwable) {
        super(throwable);
    }
}
