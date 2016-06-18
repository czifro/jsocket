package jsocket.test.unit;

import jsocket.cipher.Crypto;
import jsocket.cipher.aes.AES;
import jsocket.cipher.aes.AESImpl;
import jsocket.cipher.aes.AESKey;
import jsocket.exceptions.DecryptionFailureException;
import jsocket.exceptions.FailedToInitializeException;
import jsocket.test.mock.MockGenerator;

import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for AES encryption for Crypto service
 * @author Will Czifro
 */
public class AESCryptoUnitTests {

    @Test
    public void testEncryptAndDecrypt_ValidKey_Success() {
        try {
            AESKey mockedKey = MockGenerator.mockAESKey(0);
            Crypto crypto = new AESImpl();
            ((AES)crypto).init(mockedKey);
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
        AESKey mockedKey = MockGenerator.mockAESKey(0);
        Crypto crypto = new AESImpl();
        ((AES)crypto).init(mockedKey);
        String msg = "Hello World";

        byte[] cipher = Crypto.encryptStringToByteArray(msg, crypto);

        String plaintext = Crypto.decryptByteArrayToString(cipher, crypto);

        assertThat(plaintext.equals(msg)).isTrue();
    }

    @Test
    public void testInitialize_InvalidKey_Throws_FailedToInitializeException() {
        AESKey mockedKey = MockGenerator.mockAESKey(1);
        Crypto crypto = new AESImpl();
        assertThatThrownBy(() -> ((AES)crypto).init(mockedKey))
                .isInstanceOf(FailedToInitializeException.class);
    }

    @Test
    public void testDecrypt_InvalidInput_Throws_DecryptionFailureException() {
        AESKey mockedKey = MockGenerator.mockAESKey(0);
        Crypto crypto = new AESImpl();
        ((AES)crypto).init(mockedKey);
        String cipher = "bNAilfHOoVNaUzsTGUB5pA==";
        assertThatThrownBy(() -> crypto.decrypt(cipher.getBytes()))
                .isInstanceOf(DecryptionFailureException.class);
    }
}
