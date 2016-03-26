package jsocket.exceptions;

/**
 * Used if encryption key is already set for a cryptographic service
 * @author Will Czifro
 * @version 0.1.0
 */
public class CryptoKeyAlreadySetException extends RuntimeException {

    public CryptoKeyAlreadySetException() {
        super("Encryption Key is already set");
    }
}
