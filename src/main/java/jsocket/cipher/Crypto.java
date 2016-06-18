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
 * A cryptographic service for encrypting/decrypting data
 * This is a base interface in the cryptographic hierarchy
 * @author Will Czifro
 * @version 0.1.0
 */
public interface Crypto {

    String DEFAULT_AES = "AES", DEFAULT_RSA = "RSA";

    /**
     * Encrypts data using an underlying algorithm
     * @param data a byte array that is to be encrypted
     * @return a byte array that has been processed by the encryption algorithm
     */
    byte[] encrypt(byte[] data);

    /**
     * Decrypts a cipher using an underlying algorithm
     * @param cipher a byte array that is to be decrypted
     * @return a byte array that has been processed by the decryption algorithm
     */
    byte[] decrypt(byte[] cipher);

    /**
     * Returns a new instance of a Crypto object that uses a specified
     * algorithm.
     * @param algorithm a string value that specifies which underlying algorithm to use
     * @return a Crypto instance
     */
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

    /**
     * Allows for a single method call to encrypt a string
     * @param plainText a string vale that will be encrypted
     * @param crypto a cryptographic service that will encrypt the string
     * @return a byte array that is the encrypted version of the string
     */
    static byte[] encryptStringToByteArray(String plainText, Crypto crypto) {
        String encoded = new BASE64Encoder().encode(plainText.getBytes());
        byte[] data = crypto.encrypt(encoded.getBytes());
        return data;
    }

    /**
     * Allows for a single method call to decrypt a byte array into a string
     * @param cipher an encrypted byte array
     * @param crypto a cryptographic service that will decrypt the byte array
     * @return the string value that the cipher was hiding
     */
    static String decryptByteArrayToString(byte[] cipher, Crypto crypto) {
        try {
            byte[] data = crypto.decrypt(cipher);
            byte[] decoded = new BASE64Decoder().decodeBuffer(new String(data));
            return new String(decoded);
        } catch (IOException e) {
            throw new DecryptionFailureException(e);
        }
    }

    /**
     * An enum for different key sizes
     */
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
