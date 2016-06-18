package jsocket.exceptions;

/**
 * Thrown if specified key length is invalid for AES encryption
 * @author Will Czifro
 * @version 0.1.0
 */
public class InvalidAESKeyLengthException extends RuntimeException {

    public InvalidAESKeyLengthException(String length) {
        super("Invalid length: " + length);
    }
}
