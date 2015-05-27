import java.net.ServerSocket;

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
        //t.testFileTransferSocket();
        //t.testEncryptedFileTransfer();
    }

    /*public void testFileTransferSocket()
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
    }*/

}
