package jsocket.exceptions;

/**
 * @author Will Czifro
 */
public class DatagramConnectionException extends RuntimeException {

    public DatagramConnectionException(String message) {
        super(message);
    }

    public DatagramConnectionException(Throwable throwable) {
        super(throwable);
    }

    public DatagramConnectionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
