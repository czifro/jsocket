package jsocket.cipher.aes;

import jsocket.cipher.Crypto;
import jsocket.util.RandomStringUtil;

import javax.crypto.spec.SecretKeySpec;

/**
 * Cryptographic service that uses the AES algorithm
 * This is a symmetric encryption algorithm
 * @author Will Czifro
 * @version 0.1.0
 */
public interface AES extends Crypto {

    void init(AESKey key);

    AESKey getKey();

    static AESKey generateKey() {
        // todo: implement me
        String randString = RandomStringUtil.nextRandomString();
        return AESKey.wrap(randString, new SecretKeySpec(randString.getBytes(), "aes"));
    }
}
