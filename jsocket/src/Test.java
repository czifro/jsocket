import jsock.net.ftp.FileTransferSocket;
import jsock.net.ObjectSocket;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by czifro on 12/29/14. Class not usable.
 * @version 0.2.0
 */
public class Test {
    private final int PORT = 50000;
    private ServerSocket server;
    public static void main(String [] args) {
        Test m = new Test();
        //m.testObjectSocket();
        m.testFileTransferSocket();
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

        final File f = new File("/Users/czifro/Desktop/server.js");

        if (f.exists())
        {
            if (f.isFile())
            {
                try {
                    server = new ServerSocket(PORT);
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
