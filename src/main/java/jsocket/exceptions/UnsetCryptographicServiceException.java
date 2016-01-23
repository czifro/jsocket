package jsocket.exceptions;

/**
 * Used when an encrypted socket does not have a Cryptographic service set
 * @author Will Czifro
 * @version 0.1.0
 */
public class UnsetCryptographicServiceException extends RuntimeException {

    public UnsetCryptographicServiceException() {
        super("Cryptographic service must be set in order to make connection encrypted");
    }
}
