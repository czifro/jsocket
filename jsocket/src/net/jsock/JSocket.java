package net.jsock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by czifro on 12/29/14.
 * @author Will Czifro
 * @version 0.1.0
 *
 * An abstraction of a TCP Socket, abstract class
 */
public abstract class JSocket {

    protected PrintWriter writer;
    protected BufferedReader reader;
    protected Socket conn;

    public JSocket(Socket conn) throws IOException {
        this.conn = conn;
        writer = new PrintWriter(this.conn.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(this.conn.getInputStream()));
    }

    protected abstract byte[] recv();
    protected abstract byte[] recv_all(int size);
    protected abstract void send(char[] s);
}
