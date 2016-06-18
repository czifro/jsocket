package jsocket.cipher.aes;

import javax.crypto.spec.SecretKeySpec;

/**
 * Wraps around a SecretKeySpec that is used for the AES encryption
 * @author Will Czifro
 * @version 0.1.0
 */
public class AESKeyImpl implements AESKey {

    private String key;
    private SecretKeySpec keySpec;

    /**
     * Set the key string value
     * @param key string value for SecretKeySpec
     */
    public void setStringKey(String key) {
        this.key = key;
    }

    /**
     * Set the SecretKeySpec for the AES encryption
     * @param key key used for AES
     */
    public void setSecretKeySpec(SecretKeySpec key) {
        this.keySpec = key;
    }

    /**
     * Get the string key
     * @return string value
     */
    public String getStringKey() {
        return key;
    }

    /**
     * Get the SecretKeySpec used for AES encryption
     * @return SecretKeySpec
     */
    public SecretKeySpec getSecretKeySpec() {
        return keySpec;
    }
}
