package jsocket.exceptions;

/**
 * @author Will Czifro
 */
public class InvalidAESKeyLengthException extends RuntimeException {

    public InvalidAESKeyLengthException(String length) {
        super("Invalid length: " + length);
    }
}
