package jsocket.exceptions;

/**
 * Thrown if default cryptographic services fail to decrypt data
 * @author Will Czifro
 * @version 0.1.0
 */
public class DecryptionFailureException extends RuntimeException {

    public DecryptionFailureException(Throwable throwable) {
        super("Failed to decrypt bytes", throwable);
    }
}
