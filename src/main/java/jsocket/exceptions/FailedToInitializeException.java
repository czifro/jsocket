package jsocket.exceptions;

/**
 * @author Will Czifro
 */
public class FailedToInitializeException extends RuntimeException {

    public FailedToInitializeException(Throwable throwable) {
        super("Failed to initialize ciphers", throwable);
    }
}
