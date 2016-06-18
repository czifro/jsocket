package jsocket.exceptions;

/**
 * Thrown if string is not in proper JSON format
 * @author Will Czifro
 * @version 0.1.0
 */
public class MalformedJsonException extends RuntimeException {

    public MalformedJsonException(Throwable throwable) {
        super(throwable);
    }
}
