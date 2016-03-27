package jsocket.cipher.rsa;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author Will Czifro
 */
public interface RSAKey {

    PublicKey getPublicKey();

    PrivateKey getPrivateKey();

    KeyPair getKeyPair();

    void setKeyPair(KeyPair keyPair);

    static RSAKey wrap(KeyPair keyPair) {
        // todo: implement me
        RSAKey key = new RSAKeyImpl();
        key.setKeyPair(keyPair);
        return key;
    }
}
