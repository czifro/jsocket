package jsocket.exceptions;

/**
 * Thrown if ciphers have not been initialized prior to using the cryptographic service
 * @author Will Czifro
 * @version 0.1.0
 */
public class UninitializedCipherException extends RuntimeException {

    public UninitializedCipherException(String msg) {
        super(msg);
    }
}
