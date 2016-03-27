package jsocket.exceptions;

/**
 * @author Will Czifro
 */
public class EncryptionFailureException extends RuntimeException {

    public EncryptionFailureException(Throwable throwable) {
        super("Failed to encrypt bytes", throwable);
    }
}
