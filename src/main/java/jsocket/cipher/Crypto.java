package jsocket.cipher;

/**
 * Cryptographic interface
 * @author Will Czifro
 * @version 0.1.0
 */
public interface Crypto {

    byte[] encrypt(byte[] data);

    byte[] decrypt(byte[] cipher);
}
