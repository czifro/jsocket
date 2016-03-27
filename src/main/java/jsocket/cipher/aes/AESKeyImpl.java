package jsocket.cipher.aes;

import jsocket.cipher.aes.AESKey;

import javax.crypto.spec.SecretKeySpec;

/**
 * @author Will Czifro
 */
public class AESKeyImpl implements AESKey {

    private String key;
    private SecretKeySpec keySpec;

    public void setStringKey(String key) {
        this.key = key;
    }

    public void setSecretKeySpec(SecretKeySpec key) {
        this.keySpec = key;
    }

    public String getStringKey() {
        return key;
    }

    public SecretKeySpec getSecretKeySpec() {
        return keySpec;
    }
}
