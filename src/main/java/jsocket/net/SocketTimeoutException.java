package jsocket.net;

/**
 * Exception is used by {@link jsocket.net.Connection}
 * @author Will Czifro
 * @version 0.1.0
 */
public class SocketTimeoutException extends RuntimeException {

    public SocketTimeoutException(Throwable throwable) {
        super(throwable);
    }

}
