package net.jsock;

/**
 * Created by czifro on 12/29/14.
 *
 * A dumby class
 */
public class Main {
    public static void main(String [] args) {
        Main m = null;
        try {
            m = new Main();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        //m.testSend();
        //m.testReceive();
    }

    MessageSocket sock = null;

    public Main() throws Exception {
        throw new Exception("Not Implemented");

        /*ServerSocket server;
        try {
            server = new ServerSocket(50000);
            Socket conn = server.accept();
            sock = new MessageSocket(conn);

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void testSend()
    {
        String msg = "Hello World!";

        if (sock != null)
        {
            sock.send_msg(msg);
        }
    }

    public void testReceive()
    {
        if (sock != null)
        {
            String msg = sock.recv_msg();
            if (msg.equals("Hello back"))
            {
                msg = "success";
            }
        }
    }
}
