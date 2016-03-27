package jsocket.cipher.aes;

import jsocket.cipher.CryptoImpl;
import jsocket.exceptions.FailedToInitializeException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Will Czifro
 */
public class AESImpl extends CryptoImpl implements AES {

    private AESKey key = null;

    public void init(AESKey key) {
        Throwable throwable;
        try {
            this.key = key;
            encryptCipher = Cipher.getInstance("aes");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key.getSecretKeySpec());
            decryptCipher = Cipher.getInstance("aes");
            decryptCipher.init(Cipher.DECRYPT_MODE, key.getSecretKeySpec());
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

    public AESKey getKey() {
        return key;
    }
}
