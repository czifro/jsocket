package jsocket.exceptions;

/**
 * @author Will Czifro
 */
public class UnsupportedAlgorithmException extends RuntimeException {

    public UnsupportedAlgorithmException(String algorithm) {
        super(algorithm + " is currently not supported");
    }
}
