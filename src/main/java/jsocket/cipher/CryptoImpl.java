package jsocket.cipher;

import jsocket.cipher.aes.AES;
import jsocket.cipher.rsa.RSA;
import jsocket.exceptions.EncryptionFailureException;
import jsocket.exceptions.UninitializedCipherException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 * @author Will Czifro
 */
public class CryptoImpl implements Crypto {

    protected Cipher encryptCipher = null, decryptCipher = null;

    public byte[] encrypt(byte[] data) {
        throwIfUninitialized(encryptCipher);
        Throwable throwable;
        try {
            return encryptCipher.doFinal(data);
        } catch (IllegalBlockSizeException e) {
            throwable = e;
        } catch (BadPaddingException e) {
            throwable = e;
        }
        throw new EncryptionFailureException(throwable);
    }

    public byte[] decrypt(byte[] cipher) {
        throwIfUninitialized(decryptCipher);
        Throwable throwable;
        try {
            return decryptCipher.doFinal(cipher);
        } catch (IllegalBlockSizeException e) {
            throwable = e;
        } catch (BadPaddingException e) {
            throwable = e;
        }
        throw new EncryptionFailureException(throwable);
    }

    private void throwIfUninitialized(Cipher cipher) {
        if (cipher == null) {
            String msg;
            if (this instanceof AES)
                msg = "AES Algorithm has not been initialized";
            else if (this instanceof RSA)
                msg = "RSA Algorithm has not been initialized";
            else
                msg = "Algorithm has not been initialized";
            throw new UninitializedCipherException(msg);
        }
    }
}
