import jsock.crypto.RSA;
import jsock.net.MessageSocket;
import jsock.net.ObjectSocket;
import jsock.net.ftp.FileTransferSocket;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

/**
 * Created by czifro on 12/29/14. Class not usable.
 * @version 0.2.0
 */
public class Test {
    private final int PORT = 50000;
    private ServerSocket server;

    public static void main(String[] args)
    {
        System.out.println("Starting test...");

        Test t = new Test();
        t.testMessageSocket();
        //t.testObjectSocket();
        //t.testFileTransferSocket();
        //t.testEncryption();
        //t.testEncryptedConnection();
        //t.testEncryptedFileTransfer();
    }

    public void testMessageSocket()
    {
        try {
            server = new ServerSocket(PORT);
            SocketAddress addr = server.getLocalSocketAddress();
            String ip = ((InetSocketAddress)addr).getAddress().getHostAddress();
            final Socket[] conns = new Socket[2];
            final MessageSocket[] mSocks = new MessageSocket[2];
            Thread serverThread = new Thread(){
                public void run()
                {
                    try {
                        conns[0] = server.accept();
                        mSocks[0] = new MessageSocket(conns[0]);

                        String msg = mSocks[0].recv_msg();

                        if (msg.equals("2655"))
                            System.out.println("Success");

                        mSocks[0].close();
                    } catch (IOException e) {
                        e.printStackTrace();
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
            try {
                if (server != null)
                    server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void testObjectSocket()
    {
        try {
            server = new ServerSocket(PORT);
            final Socket[] conns = new Socket[2];
            final ObjectSocket[] oSocks = new ObjectSocket[2];
            Thread serverThread = new Thread(){
                public void run()
                {
                    try {
                        conns[0] = server.accept();
                        oSocks[0] = new ObjectSocket(conns[0]);
                        Person rP = (Person) oSocks[0].recv_object(Person.class);
                        Person t = rP;

                        oSocks[0].close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            serverThread.start();

            conns[1] = new Socket("10.0.0.32", PORT);
            oSocks[1] = new ObjectSocket(conns[1]);

            Person p = new Person(22, "12/08/1992", "Will");

            oSocks[1].send_object(p, Person.class);

            oSocks[1].close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (server != null)
                    server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void testFileTransferSocket()
    {

        final File f = new File("/Users/czifro/Documents/Videos/Jim Gaffigan/Jim_Gaffigan_Obsessed_HD.mp4");

        if (f.exists())
        {
            if (f.isFile())
            {
                try {
                    server = new ServerSocket(PORT, 0, InetAddress.getByName("10.0.0.32"));
                    final Socket[] conns = new Socket[2];
                    final FileTransferSocket[] fts_s = new FileTransferSocket[2];
                    Thread serverThread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                conns[0] = server.accept();
                                fts_s[0] = new FileTransferSocket(conns[0]);

                                File rF = fts_s[0].recv_file("/Users/czifro/Desktop/new_");

                                if (rF.length() == f.length())
                                {
                                    System.out.println("All bytes were transferred");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    serverThread.start();

                    conns[1] = new Socket("10.0.0.32", PORT);

                    fts_s[1] = new FileTransferSocket(conns[1]);

                    fts_s[1].send_file(f);

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (server != null)
                            server.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    public void testEncryption()
    {
        try {
            KeyPair kp = RSA.generateKeyPair(1024);
            String plainText = "Hello World!";
            RSA cipher = new RSA(kp);
            String encryptedText = cipher.encrypt_string(plainText);
            String decryptedText = cipher.decrypt_string(encryptedText);

            if (decryptedText.equals(plainText))
                System.out.println("Success");
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

    public void testEncryptedConnection()
    {
        try {
            server = new ServerSocket(PORT);
            KeyPair kp = RSA.generateKeyPair(1024);
            final String plainText = "Unencrypted Text";
            final Socket[] conns = new Socket[2];
            final MessageSocket[] mSocks = new MessageSocket[2];
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

                        if (msg.equals(plainText))
                            System.out.println(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            serverThread.start();

            conns[1] = new Socket("127.0.0.1", PORT);
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
        }
    }

    public void testEncryptedFileTransfer()
    {
        try {
            server = new ServerSocket(PORT);
            KeyPair kp = RSA.generateKeyPair(1024);
            final RSA rsa = new RSA(kp);

            final File f = new File("/Users/czifro/Desktop/server.js");

            if (f.exists())
            {
                if (f.isFile())
                {
                    try {
                        server = new ServerSocket(PORT, 0, InetAddress.getByName("10.0.0.32"));
                        final Socket[] conns = new Socket[2];
                        final FileTransferSocket[] fts_s = new FileTransferSocket[2];
                        Thread serverThread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    conns[0] = server.accept();
                                    fts_s[0] = new FileTransferSocket(conns[0]);
                                    fts_s[0].encryptConnection(rsa);

                                    File rF = fts_s[0].recv_file("/Users/czifro/Desktop/new_");

                                    if (rF.length() == f.length())
                                    {
                                        System.out.println("All bytes were transferred");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        serverThread.start();

                        conns[1] = new Socket("10.0.0.32", PORT);

                        fts_s[1] = new FileTransferSocket(conns[1]);

                        fts_s[1].encryptConnection(rsa);

                        fts_s[1].send_file(f);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (server != null)
                                server.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
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
    }

}
