package jsocket.exceptions;

/**
 * Thrown if cryptographic service does not support an algorithm
 * @author Will Czifro
 * @version 0.1.0
 */
public class UnsupportedAlgorithmException extends RuntimeException {

    public UnsupportedAlgorithmException(String algorithm) {
        super(algorithm + " is currently not supported");
    }
}
