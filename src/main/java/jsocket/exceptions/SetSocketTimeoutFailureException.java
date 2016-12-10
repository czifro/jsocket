package jsocket.exceptions;

/**
 * Exception is used by {@link jsocket.socket.Socket#setSoTimeout(int)}
 * @author Will Czifro
 * @version 0.1.0
 */
public class SetSocketTimeoutFailureException extends RuntimeException {

    public SetSocketTimeoutFailureException(Throwable throwable) {
        super(throwable);
    }

}
