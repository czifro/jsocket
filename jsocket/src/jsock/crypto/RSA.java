/*

    Copyright (C) 2015  Czifro Development

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

import jsock.enums.StringToolType;
import jsock.util.StringTool;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;

/**
 * Created by czifro on 1/23/15. Uses AES algorithm for encrypting and decrypting
 * @author William Czifro
 * @version 0.2.0
 */
public class RSA {

    private final static String ALGO = "RSA";
    private final KeyPair kp;

    private final Cipher encrypt_cipher, decrypt_cipher;

    /**
     * Initializes ciphers to use specified key for encrypting and decrypting
     * @param keyPair                    The key pair of public and private keys
     * @throws NoSuchPaddingException    Thrown if Cipher failed to initialize
     * @throws NoSuchAlgorithmException  Thrown if attempting to use invalid algorithm, AES is used
     * @throws InvalidKeyException       Thrown if key is not valid
     */
    public RSA(KeyPair keyPair) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        kp = keyPair;
        encrypt_cipher = Cipher.getInstance(ALGO);
        encrypt_cipher.init(Cipher.ENCRYPT_MODE, kp.getPublic());
        decrypt_cipher = Cipher.getInstance(ALGO);
        decrypt_cipher.init(Cipher.DECRYPT_MODE, kp.getPrivate());
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
        return StringTool.cleanString(new String(decrypted_bytes, "UTF-8"), StringToolType.ONLY_NULLS);
    }

    public static KeyPair generateKeyPair(int keysize) throws InvalidParameterException, NoSuchAlgorithmException {
        if (keysize == 1024 || keysize == 2048 || keysize == 4096)
        {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGO);
            kpg.initialize(keysize);
            KeyPair kp = kpg.generateKeyPair();

            return kp;
        }
        throw new  InvalidParameterException("Invalid length");
    }

    public PublicKey getPublicKey()
    {
        return kp.getPublic();
    }

    public PrivateKey getPrivateKey()
    {
        return kp.getPrivate();
    }

    public KeyPair getKeyPair()
    {
        return kp;
    }

}
