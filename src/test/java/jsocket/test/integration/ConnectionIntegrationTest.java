package jsocket.test.integration;

import jsocket.net.Connection;
import jsocket.net.SocketTimeoutException;
import jsocket.net.UDPConnection;
import jsocket.test.util.BiParamCallable;
import jsocket.test.util.ParamCallable;
import jsocket.util.ByteUtil;

import java.io.IOException;
import java.net.*;

import org.junit.Test;
import org.junit.After;
import static org.assertj.core.api.Assertions.*;

/**
 * @author Will Czifro
 */
public class ConnectionIntegrationTest {

    private final int PORT = 50000;
    private ServerSocket server;

    private void initialize() throws IOException {
        if (server == null || server.isClosed())
            server = new ServerSocket(PORT);
    }

    private String localIpAsString() {
        return ((InetSocketAddress)server.getLocalSocketAddress()).getAddress().getHostAddress();
    }

    @After
    public void cleanup() throws IOException {
        if (server != null && !server.isClosed())
            server.close();
    }

    private void testRunner(final Connection[] connections, ParamCallable<Connection,Void> serverRun, BiParamCallable<Connection,String,Boolean> clientCall) {
        try {
            initialize();
            Runnable run = () -> {
                try {
                    serverRun.call(connections[0]);
                } catch(Throwable t) {
                    fail("Something went wrong", t);
                }
            };
            Thread serverThread = new Thread(run);
            serverThread.start();
            assertThat(clientCall.call(connections[1],localIpAsString()));
        } catch(Throwable t) {
            fail("Something went wrong", t);
        }
    }

    /**
     * Timeout requires an actual instance of {@code java.net.Socket}
     * Mocking is not possible.
     */
    @Test
    public void testTimeoutRead_Success() {
        ParamCallable<Connection,Void> serverRun = (connection) -> {
            connection = Connection.getInstance(server.accept());
            return null;
        };
        BiParamCallable<Connection,String,Boolean> clientCall = (connection,ip) -> {
            connection = Connection.getInstance(new Socket(ip,PORT));
            connection.setSoTimeout(500);
            try {
                connection.receive();
            } catch(Throwable t) {
                return t.getClass() == SocketTimeoutException.class;
            }
            return false;
        };
        testRunner(new Connection[2], serverRun, clientCall);
    }

    @Test
    public void fullSocketTest_Success() {
        final Connection[] connections = new Connection[2];
        ParamCallable<Connection,Void> serverRun = (connection) -> {
            connection = Connection.getInstance(server.accept());
            connection.setBufferSize(1024);
            connection.send(ByteUtil.intToByteArray(31,4));
            int recVal = ByteUtil.byteArrayToInt(connection.receive());
            assertThat(recVal == 50).isTrue();
            return null;
        };
        BiParamCallable<Connection,String,Boolean> clientCall = (connection,ip) -> {
            connection = Connection.getInstance(new Socket(ip,PORT));
            connection.setBufferSize(1024);
            int recVal = ByteUtil.byteArrayToInt(connection.receive());
            connection.send(ByteUtil.intToByteArray(50,4));
            return recVal == 31;
        };
        testRunner(connections, serverRun, clientCall);
    }

    @Test
    public void fullDatagramTest_Success() {
        try {
            DatagramSocket _server = new DatagramSocket(50003);
            Runnable serverRun = () -> {
                try {
                    UDPConnection connection = new UDPConnection(_server);
                    Thread.sleep(500);
                    DatagramPacket packet = connection.receivePacket();
                    connection.setDefaultInetAddressAndPort(
                            packet.getAddress(),
                            packet.getPort());
                    connection.send(ByteUtil.intToByteArray(31,4));
                    int recVal = ByteUtil.byteArrayToInt(packet.getData());
                    assertThat(recVal == 50).isTrue();
                } catch (Throwable t) {
                    fail("Something went wrong", t);
                }
            };
            ParamCallable<InetAddress,Boolean> clientCall = (addr) -> {
                UDPConnection connection = new UDPConnection(new DatagramSocket(), addr, 50003);
                connection.send(ByteUtil.intToByteArray(50,4));
                return ByteUtil.byteArrayToInt(connection.receive()) == 31;
            };
            Thread serverThread = new Thread(serverRun);
            serverThread.start();
            InetAddress address = InetAddress.getByName("localhost");
            assertThat(clientCall.call(address));
        } catch (Throwable e) {
            fail("Something went wrong", e);
        }
    }
}
