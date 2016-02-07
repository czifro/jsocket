package jsocket.test.integration;

import jsocket.socket.Socket;
import jsocket.socket.SocketImpl;
import jsocket.socket.StringSocket;
import jsocket.socket.StringSocketImpl;
import jsocket.util.ByteUtil;
import jsocket.util.FilterFunctionType;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;

/**
 * @author Will Czifro
 */
public class StringSocketIntegrationTest {

    private final int PORT = 50000;
    private ServerSocket server;

    private void initialize() throws IOException {
        if (server == null || server.isClosed())
            server = new ServerSocket(PORT);
    }

    @Test
    public void fullStringSocketTest_Success() {
        final String message = "Hello";
        final StringSocket[] stringSockets = new StringSocket[2];

        try {
            initialize();
            SocketAddress addr = server.getLocalSocketAddress();
            String ip = ((InetSocketAddress)addr).getAddress().getHostAddress();
            Thread serverThread = new Thread(() -> {
                try {
                    stringSockets[0] = new StringSocketImpl(server.accept());

                    ((Socket)stringSockets[0]).setBufferSize(1024);
                    stringSockets[0].setFilterFunction(FilterFunctionType.NULL_CHARS);
                    stringSockets[0].useFilterFunction(true);

                    stringSockets[0].sendString(message);

                    String msg = stringSockets[0].receiveString();

                    ((Socket)stringSockets[0]).close();

                    assertThat(msg.equals("Hi there")).isTrue();
                } catch (IOException e) {
                    fail("Something went wrong", e);
                }
            });

            serverThread.start();

            stringSockets[1] = new StringSocketImpl(new java.net.Socket(ip, PORT));

            ((Socket)stringSockets[1]).setBufferSize(1024);
            stringSockets[1].setFilterFunction(FilterFunctionType.NULL_CHARS);
            stringSockets[1].useFilterFunction(true);

            String msg = stringSockets[1].receiveString();

            stringSockets[1].sendString("Hi there");

            ((Socket)stringSockets[1]).close();
            server.close();

            serverThread.join();

            assertThat(msg.equals("Hello")).isTrue();
        } catch (IOException e) {
            fail("Something went wrong", e);
        } catch (InterruptedException e) {
            fail("Something went wrong", e);
        }
    }
}
