package jsocket.cipher.rsa;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Wraps around a KeyPair that is used for RSA encryption
 * @author Will Czifro
 * @version 0.1.0
 */
public interface RSAKey {

    /**
     * Get the public key for the RSA encryption
     * @return the public key
     */
    PublicKey getPublicKey();

    /**
     * Get the private key for the RSA enryption
     * @return the private key
     */
    PrivateKey getPrivateKey();

    /**
     * Get the key pair of both the public and private keys
     * @return the key pair
     */
    KeyPair getKeyPair();

    /**
     * Set the key pair for the RSA encryption
     * @param keyPair the key pair
     */
    void setKeyPair(KeyPair keyPair);

    /**
     * Wraps an RSAKey around a KeyPair
     * @param keyPair the key pair
     * @return a new RSAKey instance
     */
    static RSAKey wrap(KeyPair keyPair) {
        // todo: implement me
        RSAKey key = new RSAKeyImpl();
        key.setKeyPair(keyPair);
        return key;
    }
}
