package jsocket.exceptions;

/**
 * @author Will Czifro
 */
public class SocketTimeoutException extends RuntimeException {

    public SocketTimeoutException(Throwable throwable) {
        super(throwable);
    }
    
}
