/*

    Copyright (C) 2015  William Czifro

    This file is part of the jsock.crypto package

    The jsock.crypto package is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    The jsock.crypto package is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with the jsock.crypto package.  If not, see <http://www.gnu.org/licenses/>.

 */

package jsock.crypto;

import jsock.enums.StringCleanType;
import jsock.util.StringCleaner;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by czifro on 1/23/15. Uses AES algorithm for encrypting and decrypting
 * @author William Czifro
 * @version 0.1.0
 */
public class AES {

    private final String aes = "AES";
    private final String privateKey;

    private final Key key;
    private final Cipher encrypt_cipher, decrypt_cipher;

    /**
     * Initializes ciphers to use specified key for encrypting and decrypting
     * @param privateKey                 The key in which bytes will be encrypted with
     * @throws NoSuchPaddingException    Thrown if Cipher failed to initialize
     * @throws NoSuchAlgorithmException  Thrown if attempting to use invalid algorithm, AES is used
     * @throws InvalidKeyException       Thrown if key is not valid
     */
    public AES(String privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        this.privateKey = privateKey;
        key = new SecretKeySpec(privateKey.getBytes(), aes);
        encrypt_cipher = Cipher.getInstance(aes);
        encrypt_cipher.init(Cipher.ENCRYPT_MODE, key);
        decrypt_cipher = Cipher.getInstance(aes);
        decrypt_cipher.init(Cipher.DECRYPT_MODE, key);
    }

    /**
     * Encrypts a byte[]
     * @param b    Bytes to be encrypted
     * @return     Encrypted bytes
     * @throws BadPaddingException        Thrown if bytes cannot be encrypted
     * @throws IllegalBlockSizeException  Thrown if bytes cannot be encrypted
     */
    public byte[] encrypt(byte[] b) throws BadPaddingException, IllegalBlockSizeException {
        byte[] encrypted_bytes = encrypt_cipher.doFinal(b);
        return encrypted_bytes;
    }

    /**
     * Encrypts a String
     * @param s    String to be encrypted
     * @return     Encrypted String
     * @throws BadPaddingException        Thrown if bytes cannot be encrypted
     * @throws IllegalBlockSizeException  Thrown if bytes cannot be encrypted
     */
    public String encrypt_string(String s) throws BadPaddingException, IllegalBlockSizeException {
        byte[] encrypted_bytes = encrypt(s.getBytes());
        return new BASE64Encoder().encode(encrypted_bytes);
    }

    /**
     * Decrypts a byte[]
     * @param b    Bytes to be decrypted
     * @return     Decrypted bytes
     * @throws BadPaddingException        Thrown if bytes cannot be decrypted
     * @throws IllegalBlockSizeException  Thrown if bytes cannot be decrypted
     */
    public byte[] decrypt(byte[] b) throws BadPaddingException, IllegalBlockSizeException {
        byte[] decrypted_bytes = decrypt_cipher.doFinal(b);
        return decrypted_bytes;
    }

    /**
     * Decrypts a String
     * @param s  String to be decrypted
     * @return   Decrypted String
     * @throws IOException                Thrown if bytes cannot be converted to a String
     * @throws BadPaddingException        Thrown if bytes cannot be decrypted
     * @throws IllegalBlockSizeException  Thrown if bytes cannot be decrypted
     */
    public String decrypt_string(String s) throws IOException, BadPaddingException, IllegalBlockSizeException {
        byte[] decoded = new BASE64Decoder().decodeBuffer(s);
        byte[] decrypted_bytes = decrypt(decoded);
        return StringCleaner.cleanString(new String(decrypted_bytes, "UTF-8"), StringCleanType.ONLY_NULLS);
    }

    /**
     * @return Returns the private key being used
     */
    public String getPrivateKey()
    {
        return privateKey;
    }
}
