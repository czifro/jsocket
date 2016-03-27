package jsocket.cipher.aes;

import jsocket.cipher.Crypto;
import jsocket.cipher.KeySize;
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

    static AESKey generateKey(KeySize size) {
        // todo: implement me
        // convert key size to multiple of 5 by adding extra bits
        // example: 1024 % 5 = 4
        // 5 - 4 = 1
        // 1024 + 1 = 1025
        int bitLength = size.toInt() + (5 - (size.toInt() % 5));
        String randString = RandomStringUtil.nextRandomString(bitLength);
        return AESKey.wrap(randString, new SecretKeySpec(randString.getBytes(), "aes"));
    }
}
