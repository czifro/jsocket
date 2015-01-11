package net.jsock;

import java.net.Socket;

/**
 * Created by czifro on 12/29/14.
 *
 * A dumby class
 */
public class Main {
    public static void main(String [] args) {
        Main m = new Main();
        try {
            m.testConnectToServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    MessageSocket sock = null;

    public void testConnectToServer() throws Exception
    {
        Socket conn = new Socket("10.0.0.32", 50000);

        try {
            sock = new MessageSocket(conn);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        if (sock == null)
        {
            throw new Exception("");
        }

        String msg = sock.recv_msg();

        if (msg.equals("<QCSERVER>Identify"))
        {
            sock.send_msg("<QCCOPTER>ID: Test");

            //msg = sock.recv_msg();

            msg = "<QCCOPTER><SUSPENDCONNECTION>";

            sock.send_msg("<QCCOPTER-MSGSIZE>" + Integer.toString(msg.getBytes().length));

            String t = sock.recv_msg();

            sock.send_msg(msg);

            sock.close();

            conn = new Socket("10.0.0.32", 50000);

            try {
                sock = new MessageSocket(conn);
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            if (sock == null)
            {
                throw new Exception("");
            }

            msg = sock.recv_msg();

            if (msg.equals("<QCSERVER>Identify"))
            {
                sock.send_msg("<QCCOPTER>Renew, ID: Test");

                msg = sock.recv_msg();

                if (msg.equals("Success"))
                {
                    System.out.println(msg);
                }
            }
        }
    }
}
