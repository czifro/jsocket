package jsocket.exceptions;

/**
 * Thrown if default cryptographic services fail to encrypt data
 * @author Will Czifro
 * @v
 */
public class EncryptionFailureException extends RuntimeException {

    public EncryptionFailureException(Throwable throwable) {
        super("Failed to encrypt bytes", throwable);
    }
}
