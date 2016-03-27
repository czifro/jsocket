package jsocket.cipher.rsa;

import jsocket.cipher.Crypto;
import jsocket.cipher.KeySize;

import java.security.*;

/**
 * Cryptographic service that uses the RSA algorithm
 * This is an asymmetric encryption algorithm
 * @author Will Czifro
 * @version 0.1.0
 */
public interface RSA extends Crypto {

    void init(RSAKey key);

    RSAKey getRSAKey();

    static KeyPair generateKeyPair(KeySize size) {
        // todo: implement me
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(size.toInt());
            return kpg.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
