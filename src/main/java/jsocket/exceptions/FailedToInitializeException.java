package jsocket.exceptions;

/**
 * Thrown if ciphers used by cryptographic services fail to initialize
 * @author Will Czifro
 * @version 0.1.0
 */
public class FailedToInitializeException extends RuntimeException {

    public FailedToInitializeException(Throwable throwable) {
        super("Failed to initialize ciphers", throwable);
    }
}
