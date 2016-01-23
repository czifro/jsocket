package jsocket.exceptions;

/**
 * Used when exception occurs handling I/O streams and data
 * @author Will Czifro
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
