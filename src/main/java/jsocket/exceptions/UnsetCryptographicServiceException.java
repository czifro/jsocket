package jsocket.exceptions;

/**
 * @author Will Czifro
 */
public class UnsetCryptographicServiceException extends RuntimeException {

    public UnsetCryptographicServiceException() {
        super("Cryptographic service must be set in order to make connection encrypted");
    }
}
