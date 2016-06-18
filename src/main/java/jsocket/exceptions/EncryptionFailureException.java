package jsocket.exceptions;

/**
 * Thrown if default cryptographic services fail to encrypt data
 * @author Will Czifro
 * @version 0.1.0
 */
public class EncryptionFailureException extends RuntimeException {

    public EncryptionFailureException(Throwable throwable) {
        super("Failed to encrypt bytes", throwable);
    }
}
