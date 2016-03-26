package jsocket.test.integration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.*;

import jsocket.exceptions.SocketStreamException;
import jsocket.socket.Socket;
import jsocket.socket.SocketImpl;
import jsocket.util.ByteUtil;
import org.junit.Test;

/**
 * @author Will Czifro
 */
public class SocketIntegrationTest {

    private final int PORT = 50000;
    private ServerSocket server;

    private void initialize() throws IOException {
        if (server == null || server.isClosed())
            server = new ServerSocket(PORT);
    }

    @Test
    public void fullSocketTest_Success() {
        final int val = 31;
        final Socket[] sockets = new Socket[2];

        try {
            initialize();
            SocketAddress addr = server.getLocalSocketAddress();
            String ip = ((InetSocketAddress)addr).getAddress().getHostAddress();
            Thread serverThread = new Thread(() -> {
               try {
                   sockets[0] = new SocketImpl(server.accept());

                   sockets[0].setBufferSize(1024);

                   sockets[0].send(ByteUtil.intToByteArray(val, 4));

                   byte[] rec = sockets[0].receive();

                   int recVal = ByteUtil.byteArrayToInt(rec);

                   assertThat(recVal == 50).isTrue();
               } catch (IOException e) {
                   e.printStackTrace();
               }
            });

            serverThread.start();

            sockets[1] = new SocketImpl(new java.net.Socket(ip, PORT));

            sockets[1].setBufferSize(1024);

            byte[] rec = sockets[1].receive();

            int recVal = ByteUtil.byteArrayToInt(rec);

            rec = ByteUtil.intToByteArray(50, 4);

            sockets[1].send(rec);

            assertThat(recVal == val).isTrue();
        } catch (IOException e) {
            fail("Something went wrong", e);
        }
    }
}
