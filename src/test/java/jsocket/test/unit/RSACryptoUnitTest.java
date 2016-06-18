package jsocket.test.unit;

import jsocket.cipher.Crypto;
import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for RSA encryption for the Crypto service
 * @author Will Czifro
 */
public class RSACryptoUnitTest {

    @Test
    public void testEncryptAndDecrypt_ValidKey_Success() {
        try {
            Crypto crypto = Crypto.newInstance(Crypto.DEFAULT_RSA);
            String msg = "Hello World";

            String encoded = new BASE64Encoder().encode(msg.getBytes());
            byte[] data = crypto.encrypt(encoded.getBytes());

            data = crypto.decrypt(data);//cipher.getBytes());
            byte[] decoded = new BASE64Decoder().decodeBuffer(new String(data));
            String decryptedString = new String(decoded);
            assertThat(decryptedString.equals(msg)).isTrue();
        } catch (UnsupportedEncodingException e) {
            fail("Something went wrong", e);
        } catch (IOException e) {
            fail("Something went wrong", e);
        }
    }

    @Test
    public void testStaticEncryptAndDecryptStrings_ValidKey_Success() {
        Crypto crypto = Crypto.newInstance(Crypto.DEFAULT_RSA);
        String msg = "Hello World";

        byte[] cipher = Crypto.encryptStringToByteArray(msg, crypto);

        String plaintext = Crypto.decryptByteArrayToString(cipher, crypto);

        assertThat(plaintext.equals(msg)).isTrue();
    }
}
