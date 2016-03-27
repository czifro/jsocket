package jsocket.cipher.rsa;

import jsocket.cipher.CryptoImpl;
import jsocket.exceptions.FailedToInitializeException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

/**
 * @author Will Czifro
 */
public class RSAImpl extends CryptoImpl implements RSA {

    public RSAKey key = null;

    public void init(RSAKey key) {
        Throwable throwable;
        try {
            this.key = key;
            encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key.getPublicKey());
            decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.init(Cipher.DECRYPT_MODE, key.getPrivateKey());
            return;
        } catch (NoSuchPaddingException e) {
            throwable = e;
        } catch (NoSuchAlgorithmException e) {
            throwable = e;
        } catch (InvalidKeyException e) {
            throwable = e;
        }
        throw new FailedToInitializeException(throwable);
    }

    public RSAKey getRSAKey() {
        return null;
    }
}
