package jsocket.exceptions;

/**
 * @author Will Czifro
 */
public class DecryptionFailureException extends RuntimeException {

    public DecryptionFailureException(Throwable throwable) {
        super("Failed to decrypt bytes", throwable);
    }
}
