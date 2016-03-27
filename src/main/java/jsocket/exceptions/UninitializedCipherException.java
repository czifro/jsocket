package jsocket.exceptions;

/**
 * @author Will Czifro
 */
public class UninitializedCipherException extends RuntimeException {

    public UninitializedCipherException(String msg) {
        super(msg);
    }
}
