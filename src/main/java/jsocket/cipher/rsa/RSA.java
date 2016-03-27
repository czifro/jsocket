package jsocket.cipher.rsa;

import jsocket.cipher.Crypto;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Cryptographic service that uses the RSA algorithm
 * This is an asymmetric encryption algorithm
 * @author Will Czifro
 * @version 0.1.0
 */
public interface RSA extends Crypto {

    PublicKey getPublicKey();

    PrivateKey getPrivateKey();

    KeyPair getKeyPair();

    void init(KeyPair keyPair);

    static KeyPair generateKeyPair() {
        // todo: implement me
        return null;
    }
}
