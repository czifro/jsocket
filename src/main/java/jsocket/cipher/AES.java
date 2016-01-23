package jsocket.cipher;

/**
 * Cryptographic service that uses the AES algorithm
 * This is a symmetric encryption algorithm
 * @author Will Czifro
 * @version 0.1.0
 */
public interface AES extends Crypto {

    void setKey(String key);

    String getKey();

    static String generateKey() {
        // todo: implement me
        return null;
    }
}
