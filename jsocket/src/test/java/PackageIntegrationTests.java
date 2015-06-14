/**
 * Created by czifro on 5/25/15.
 */

import jsock.crypto.RSA;
import jsock.enums.FunctionType;
import jsock.net.JSocket;
import jsock.net.MessageSocket;
import jsock.net.ObjectSocket;
import jsock.util.ByteTool;
import jsock.util.FunctionTool;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public class PackageIntegrationTests {
    private final int PORT = 50000;
    private ServerSocket server;

    private void initialize() throws IOException {
        if (server == null || server.isClosed())
            server = new ServerSocket(PORT);
    }

    @Test
    public void testJsocket() {
        final int val = 31; // value to be sent
        final Socket[] conns = new Socket[2];
        final JSocket[] jsocks = new JSocket[2];
        try {
            initialize();
            SocketAddress addr = server.getLocalSocketAddress();
            String ip = ((InetSocketAddress)addr).getAddress().getHostAddress();
            Thread serverThread = new Thread(){
                public void run()
                {
                    try {
                        conns[0] = server.accept();
                        jsocks[0] = new JSocket(conns[0]);

                        Assert.assertFalse(val != ByteTool.byteArrayToInt(jsocks[0].recv()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (jsocks[0] != null)
                            jsocks[0].close();
                    }
                }
            };
            serverThread.start();

            conns[1] = new Socket(ip, PORT);
            jsocks[1] = new JSocket(conns[1]);

            jsocks[1].send(ByteTool.intToByteArray(val, 4));

            jsocks[1].close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (jsocks[1] != null)
                jsocks[1].close();
            try {
                if (server != null)
                    server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void testMessageSocket() {
        final Socket[] conns = new Socket[2];
        final MessageSocket[] mSocks = new MessageSocket[2];
        try {
            initialize();
            SocketAddress addr = server.getLocalSocketAddress();
            String ip = ((InetSocketAddress)addr).getAddress().getHostAddress();
            Thread serverThread = new Thread(){
                public void run()
                {
                    try {
                        conns[0] = server.accept();
                        mSocks[0] = new MessageSocket(conns[0]);

                        String msg = mSocks[0].recv_msg();

                        Assert.assertEquals("2655", msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (mSocks[0] != null)
                            mSocks[0].close();
                    }
                }
            };
            serverThread.start();

            conns[1] = new Socket(ip, PORT);
            mSocks[1] = new MessageSocket(conns[1]);

            mSocks[1].send_msg("2655");

            mSocks[1].close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mSocks[1] != null)
                mSocks[1].close();
            try {
                if (server != null)
                    server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void testMessageSocketWithFunction() {
        final Socket[] conns = new Socket[2];
        final MessageSocket[] mSocks = new MessageSocket[2];
        try {
            initialize();
            SocketAddress addr = server.getLocalSocketAddress();
            String ip = ((InetSocketAddress)addr).getAddress().getHostAddress();
            Thread serverThread = new Thread(){
                public void run()
                {
                    try {
                        conns[0] = server.accept();
                        mSocks[0] = new MessageSocket(conns[0]);

                        String msg = mSocks[0].recv_sanitized_msg(FunctionTool.sanitizationFunction(FunctionType.ONLY_NULLS));

                        Assert.assertEquals("2655", msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (mSocks[0] != null)
                            mSocks[0].close();
                    }
                }
            };
            serverThread.start();

            conns[1] = new Socket(ip, PORT);
            mSocks[1] = new MessageSocket(conns[1]);

            mSocks[1].send_msg("2655");

            mSocks[1].close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mSocks[1] != null)
                mSocks[1].close();
            try {
                if (server != null)
                    server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void testObjectSocket() {
        final Person p = new Person(22, "12/08/1992", "Will");
        final Socket[] conns = new Socket[2];
        final ObjectSocket[] oSocks = new ObjectSocket[2];
        try {
            initialize();
            SocketAddress addr = server.getLocalSocketAddress();
            String ip = ((InetSocketAddress)addr).getAddress().getHostAddress();
            Thread serverThread = new Thread(){
                public void run()
                {
                    try {
                        conns[0] = server.accept();
                        oSocks[0] = new ObjectSocket(conns[0]);
                        Person rP = (Person) oSocks[0].recv_object(Person.class);

                        Assert.assertFalse(!p.areEqual(rP));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (oSocks[0] != null)
                            oSocks[0].close();
                    }
                }
            };
            serverThread.start();

            conns[1] = new Socket(ip, PORT);
            oSocks[1] = new ObjectSocket(conns[1]);

            oSocks[1].send_object(p, Person.class);

            oSocks[1].close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oSocks[1] != null)
                oSocks[1].close();
            try {
                if (server != null)
                    server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void testEncryption() {
        try {
            KeyPair kp = RSA.generateKeyPair(1024);
            String plainText = "Hello World!";
            RSA cipher = new RSA(kp);
            String encryptedText = cipher.encrypt_string(plainText);
            String decryptedText = cipher.decrypt_string(encryptedText);

            Assert.assertEquals(decryptedText, plainText);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testEncryptedConnection() {
        final Socket[] conns = new Socket[2];
        final MessageSocket[] mSocks = new MessageSocket[2];
        try {
            initialize();
            SocketAddress addr = server.getLocalSocketAddress();
            String ip = ((InetSocketAddress)addr).getAddress().getHostAddress();
            KeyPair kp = RSA.generateKeyPair(1024);
            final String plainText = "Unencrypted Text";
            final RSA rsa = new RSA(kp);
            Thread serverThread = new Thread()
            {
                public void run()
                {
                    try {
                        conns[0] = server.accept();
                        mSocks[0] = new MessageSocket(conns[0]);
                        mSocks[0].encryptConnection(rsa);

                        String msg = mSocks[0].recv_msg();

                        Assert.assertEquals(msg, plainText);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (mSocks[0] != null)
                            mSocks[0].close();
                    }
                }
            };
            serverThread.start();

            conns[1] = new Socket(ip, PORT);
            mSocks[1] = new MessageSocket(conns[1]);
            mSocks[1].encryptConnection(rsa);

            mSocks[1].send_msg(plainText);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }finally {
            if (mSocks[1] != null)
                mSocks[1].close();
            try {
                if (server != null)
                    server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class Person {
        public int Age;
        private String DOB;
        public String Name;

        public Person(int Age, String DOB, String Name)
        {
            this.Age = Age;
            this.DOB = DOB;
            this.Name = Name;
        }

        public boolean areEqual(Person p) {
            return this.Age == p.Age && this.Name.equals(p.Name) && this.DOB.equals(p.DOB);
        }
    }
}
