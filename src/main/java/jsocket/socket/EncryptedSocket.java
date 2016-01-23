package jsocket.socket;

import jsocket.cipher.Crypto;

/**
 * Abstracts jsocket.socket.Socket to add an encryption step to sending and receiving bytes
 * It is suggested that implementation extend jsocket.socket.SocketImpl
 * Default implementation uses simple protocol to ensure all bytes are transferred
 * @author Will Czifro
 * @version 0.1.0
 */
public interface EncryptedSocket {

    /**
     * Sets the service used to encrypt and decrypt data
     * @param encryptionService Cryptographic service
     */
    void setEncryptionService(Crypto encryptionService);

    /**
     * Receives encrypted data and decrypts it
     * @return decrypted bytes
     */
    byte[] receiveEncrypted();

    /**
     * Receives encrypted data of certain size and decrypts it
     * @param size number of bytes to read in
     * @return decrypted bytes
     */
    byte[] receiveEncryptedAll(int size);

    /**
     * Encrypts bytes before sending it
     * @param data
     */
    void sendEncrypted(byte[] data);
}
