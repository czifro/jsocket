package jsocket.test.integration;

import jsocket.socket.*;
import jsocket.test.mock.MockPerson;
import jsocket.util.FilterFunctionType;
import jsocket.util.JsonServiceImpl;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * @author Will Czifro
 */
public class ObjectSocketIntegrationTest {

    private final int PORT = 50002;
    private ServerSocket server;

    private void initialize() throws IOException {
        if (server == null || server.isClosed())
            server = new ServerSocket(PORT);
    }

    @Test
    public void fullObjectSocketTest_Success() {
        final String message = "Hello";
        final ObjectSocket[] objectSockets = new ObjectSocket[2];

        try {
            initialize();
            SocketAddress addr = server.getLocalSocketAddress();
            String ip = ((InetSocketAddress)addr).getAddress().getHostAddress();
            Thread serverThread = new Thread(() -> {
                try {
                    MockPerson p = new MockPerson();
                    p.Name = "Will Czifro";

                    objectSockets[0] = new ObjectSocketImpl(server.accept());

                    objectSockets[0].setBufferSize(1024);
                    objectSockets[0].setJsonTool(new JsonServiceImpl());
                    objectSockets[0].setFilterFunction(FilterFunctionType.NULL_CHARS);
                    objectSockets[0].useFilterFunction(true);

                    objectSockets[0].sendObject(p);

                    MockPerson p1 = objectSockets[0].receiveObject(MockPerson.class);

                    objectSockets[0].close();

                    assertThat(p1.Name.equals("Hannah Lee")).isTrue();
                } catch (IOException e) {
                    fail("Something went wrong", e);
                }
            });

            serverThread.start();

            MockPerson p = new MockPerson();
            p.Name = "Hannah Lee";

            objectSockets[1] = new ObjectSocketImpl(new java.net.Socket(ip, PORT));

            objectSockets[1].setBufferSize(1024);
            objectSockets[1].setJsonTool(new JsonServiceImpl());
            objectSockets[1].setFilterFunction(FilterFunctionType.NULL_CHARS);
            objectSockets[1].useFilterFunction(true);

            MockPerson p1 = objectSockets[1].receiveObject(MockPerson.class);

            objectSockets[1].sendObject(p);

            objectSockets[1].close();
            server.close();

            serverThread.join();

            assertThat(p1.Name.equals("Will Czifro")).isTrue();
        } catch (IOException e) {
            fail("Something went wrong", e);
        } catch (InterruptedException e) {
            fail("Something went wrong", e);
        }
    }
}