package jsocket.test.integration;

import jsocket.datagram.Datagram;
import jsocket.datagram.DatagramImpl;
import jsocket.exceptions.DatagramConnectionException;
import jsocket.util.ByteUtil;
import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Will Czifro
 */
public class DatagramIntegrationTest {

    private final int PORT = 50003;
    private DatagramSocket server;

    private void initialize() throws IOException {
        if (server == null || server.isClosed()) {
            server = new DatagramSocket(PORT);
        }
    }

    @Test
    public void fullDatagramTest_Success() {
        final int val = 31;
        final Datagram[] datagrams = new Datagram[2];

        try {
            initialize();
            final InetAddress address =
                    InetAddress.getByName("localhost");
            Thread serverThread = new Thread(() -> {
                try {
                    datagrams[0] = new DatagramImpl(server);
                    datagrams[0].setBufferSize(128);
                    Thread.sleep(500);
                    DatagramPacket packet = datagrams[0].receivePacket();
                    datagrams[0].setDefaultInetAddressAndPort(
                            datagrams[0].getReceivedInetAddress(),
                            datagrams[0].getReceivedPort());
                    datagrams[0].send(ByteUtil.intToByteArray(val, 4));

                    int recVal = ByteUtil.byteArrayToInt(packet.getData());

                    assertThat(recVal == 50).isTrue();
                } catch (DatagramConnectionException e) {
                    fail("Something went wrong", e);
                } catch (InterruptedException e) {
                    fail("Something went wrong", e);
                }
            });

            serverThread.start();

            datagrams[1] = new DatagramImpl(
                    new DatagramSocket(),
                    address, PORT);
            datagrams[1].setBufferSize(128);
            datagrams[1].send(ByteUtil.intToByteArray(50, 4));
            byte[] rec = datagrams[1].receive();
            int recVal = ByteUtil.byteArrayToInt(rec);
            assertThat(recVal == val).isTrue();
        } catch (IOException e) {
            fail("Something went wrong", e);
        }
    }
}
