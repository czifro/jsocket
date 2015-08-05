package jsock.interfaces;

import jsock.crypto.RSA;

/**
 * Created by czifro on 2/2/15. Methods to be implemented to encrypt a socket connection
 */
public interface IEncryptSocket {

    public void encryptConnection(RSA rsa);

    public boolean connectionIsEncrypted();

    public void send_encrypted(byte[] bytes);

    public byte[] recv_encrypted() throws Exception;

}
