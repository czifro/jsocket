package jsocket.exceptions;

/**
 * Exception is used by {@link jsocket.datagram.Datagram}
 * @author Will Czifro
 * @version 0.1.0
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
