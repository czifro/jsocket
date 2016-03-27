package jsocket.cipher.aes;

import javax.crypto.spec.SecretKeySpec;

/**
 * @author Will Czifro
 */
public interface AESKey {

    void setStringKey(String key);

    void setSecretKeySpec(SecretKeySpec key);

    String getStringKey();

    SecretKeySpec getSecretKeySpec();

    static AESKey wrap(String key, SecretKeySpec keySpec) {
        AESKey aesKey = new AESKeyImpl();
        aesKey.setStringKey(key);
        aesKey.setSecretKeySpec(keySpec);
        return aesKey;
    }
}
