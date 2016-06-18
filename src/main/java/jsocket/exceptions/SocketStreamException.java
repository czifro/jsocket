package jsocket.exceptions;

/**
 * Thrown if an exception occurs while performing an I/O operation on a stream
 * @author Will Czifro
 * @version 0.1.0
 */
public class SocketStreamException extends RuntimeException {

    public SocketStreamException(String message) {
        super(message);
    }

    public SocketStreamException(Throwable throwable) {
        super(throwable);
    }

    public SocketStreamException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
