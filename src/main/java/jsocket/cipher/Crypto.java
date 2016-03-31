package jsocket.cipher;

import jsocket.cipher.aes.AES;
import jsocket.cipher.aes.AESImpl;
import jsocket.cipher.rsa.RSA;
import jsocket.cipher.rsa.RSAImpl;
import jsocket.exceptions.DecryptionFailureException;
import jsocket.exceptions.UnsupportedAlgorithmException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Cryptographic interface
 * @author Will Czifro
 * @version 0.1.0
 */
public interface Crypto {

    String DEFAULT_AES = "AES", DEFAULT_RSA = "RSA";

    byte[] encrypt(byte[] data);

    byte[] decrypt(byte[] cipher);

    static Crypto newInstance(String algorithm) {
        if (!algorithm.equals(DEFAULT_AES) && !algorithm.equals(DEFAULT_RSA))
            throw new UnsupportedAlgorithmException(algorithm);
        Crypto instance = null;
        if (algorithm.equals(DEFAULT_AES)) {
            instance = new AESImpl();
            ((AES)instance).init(AES.generateKey(KeySize.BIT_LENGTH_AES_128));
        }
        else {
            instance = new RSAImpl();
            ((RSA)instance).init(RSA.generateKeyPair(KeySize.BIT_LENGTH_4096));
        }
        return instance;
    }

    static byte[] encryptStringToByteArray(String plainText, Crypto crypto) {
        String encoded = new BASE64Encoder().encode(plainText.getBytes());
        byte[] data = crypto.encrypt(encoded.getBytes());
        return data;
    }

    static String decryptByteArrayToString(byte[] cipher, Crypto crypto) {
        try {
            byte[] data = crypto.decrypt(cipher);
            byte[] decoded = new BASE64Decoder().decodeBuffer(new String(data));
            return new String(decoded);
        } catch (IOException e) {
            throw new DecryptionFailureException(e);
        }
    }

    enum KeySize {

        BIT_LENGTH_AES_128(128),
        BIT_LENGTH_1024(1024),
        BIT_LENGTH_2048(2048),
        BIT_LENGTH_4096(4096);

        private int size;

        KeySize(int value) {
            size = value;
        }

        public int toInt() {
            return size;
        }
    }
}
