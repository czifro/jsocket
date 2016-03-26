package jsocket.cipher;

import javax.crypto.spec.SecretKeySpec;

/**
 * @author Will Czifro
 */
public interface AESKey {

    void setStringKey(String key);

    void setSecretKeySpec(SecretKeySpec key);

    String getStringKey();

    SecretKeySpec getSecretKeySpec();

    static AESKey wrap(String key) {
        // todo: implement me
        return null;
    }
}
