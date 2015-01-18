package net.jsock;

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
        m.testObjectSocket();
        try {

        } catch (Exception e) {
            e.printStackTrace();
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            serverThread.start();

            conns[1] = new Socket("10.0.0.32", PORT);
            oSocks[1] = new ObjectSocket(conns[1]);

            Person p = new Person(22, "12/08/1992", "Will");
            Class<?> c = Person.class;

            oSocks[1].send_object(p, Person.class);


        } catch (IOException e) {
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
