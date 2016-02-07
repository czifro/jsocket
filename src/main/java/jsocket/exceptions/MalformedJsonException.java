package jsocket.exceptions;

/**
 * @author Will Czifro
 */
public class MalformedJsonException extends RuntimeException {

    public MalformedJsonException(Throwable throwable) {
        super(throwable);
    }
}
