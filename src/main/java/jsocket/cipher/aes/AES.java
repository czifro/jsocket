package jsocket.cipher.aes;

import jsocket.cipher.Crypto;
import jsocket.exceptions.InvalidAESKeyLengthException;
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

    static AESKey generateKey(Crypto.KeySize size) {
        if (size != KeySize.BIT_LENGTH_AES_128)
            throw new InvalidAESKeyLengthException(Integer.toString(size.toInt()));
        //
        int bitLength = 80;
        String randString = "";
        while ((randString = RandomStringUtil.nextRandomString(bitLength)).length() != 16);
        return AESKey.wrap(randString, new SecretKeySpec(randString.getBytes(), "AES"));
    }
}
