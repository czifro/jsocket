package jsocket.cipher.aes;

import javax.crypto.spec.SecretKeySpec;

/**
 * Wraps around a SecretKeySpec that is used for the AES encryption
 * @author Will Czifro
 * @version 0.1.0
 */
public interface AESKey {

    /**
     * Set the key string value
     * @param key string value for SecretKeySpec
     */
    void setStringKey(String key);

    /**
     * Set the SecretKeySpec for the AES encryption
     * @param key key used for AES
     */
    void setSecretKeySpec(SecretKeySpec key);

    /**
     * Get the string key
     * @return string value
     */
    String getStringKey();

    /**
     * Get the SecretKeySpec used for AES encryption
     * @return SecretKeySpec
     */
    SecretKeySpec getSecretKeySpec();

    /**
     * Wraps a new instance of AESKey around the key used for AES encryption
     * Uses default implementation
     * @param key string key
     * @param keySpec SecretKeySpec key
     * @return a new instance of the default AESKey
     */
    static AESKey wrap(String key, SecretKeySpec keySpec) {
        AESKey aesKey = new AESKeyImpl();
        aesKey.setStringKey(key);
        aesKey.setSecretKeySpec(keySpec);
        return aesKey;
    }
}
