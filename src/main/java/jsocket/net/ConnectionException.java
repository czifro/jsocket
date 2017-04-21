package jsocket.net;

/**
 * Used by {@link TCPConnection} and {@link UDPConnection} 
 * when transmission or reception fails
 * @author Will Czifro
 * @version 0.1.0
 */
public class ConnectionException extends RuntimeException {

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(Throwable throwable) {
        super(throwable);
    }

    public ConnectionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
