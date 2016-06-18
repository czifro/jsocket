package jsocket.cipher.rsa;

import jsocket.cipher.Crypto;

import java.security.*;

/**
 * Cryptographic service that uses the RSA algorithm
 * This is an asymmetric encryption algorithm
 * @author Will Czifro
 * @version 0.1.0
 */
public interface RSA extends Crypto {

    /**
     * Initialize the service with an RSAKey
     * @param key the RSA key
     */
    void init(RSAKey key);

    /**
     * Get the RSA key
     * @return
     */
    RSAKey getRSAKey();

    /**
     * Generates a RSA key of specified size
     * @param size size of RSA key
     * @return a new RSA key
     */
    static RSAKey generateKeyPair(Crypto.KeySize size) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(size.toInt());
            return RSAKey.wrap(kpg.generateKeyPair());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
