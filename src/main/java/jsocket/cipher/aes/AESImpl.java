package jsocket.cipher.aes;

import jsocket.cipher.CryptoImpl;
import jsocket.exceptions.FailedToInitializeException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

/**
 * @author Will Czifro
 */
public class AESImpl extends CryptoImpl implements AES {

    private AESKey key = null;

    public void init(AESKey key) {
        Throwable throwable;
        try {
            this.key = key;
            encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key.getSecretKeySpec());
            AlgorithmParameters params = encryptCipher.getParameters();
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
            decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            decryptCipher.init(Cipher.DECRYPT_MODE, key.getSecretKeySpec(), new IvParameterSpec(iv));
            return;
        } catch (NoSuchPaddingException e) {
            throwable = e;
        } catch (NoSuchAlgorithmException e) {
            throwable = e;
        } catch (InvalidKeyException e) {
            throwable = e;
        } catch (InvalidAlgorithmParameterException e) {
            throwable = e;
        } catch (InvalidParameterSpecException e) {
            throwable = e;
        }
        throw new FailedToInitializeException(throwable);
    }

    public AESKey getKey() {
        return key;
    }
}
