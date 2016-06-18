package jsocket.cipher.rsa;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Wraps around a KeyPair that is used for RSA encryption
 * Default implementation of RSAKey
 * @author Will Czifro
 * @version 0.1.0
 */
public class RSAKeyImpl implements RSAKey {

    private KeyPair keyPair = null;

    /**
     * Get the public key for the RSA encryption
     * @return the public key
     */
    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    /**
     * Get the private key for the RSA enryption
     * @return the private key
     */
    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    /**
     * Get the key pair of both the public and private keys
     * @return the key pair
     */
    public KeyPair getKeyPair() {
        return keyPair;
    }

    /**
     * Set the key pair for the RSA encryption
     * @param keyPair the key pair
     */
    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }
}
