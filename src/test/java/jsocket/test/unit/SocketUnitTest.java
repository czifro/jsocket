package jsocket.test.unit;

import jsocket.exceptions.SocketStreamException;
import jsocket.socket.Socket;
import jsocket.test.mock.MockGenerator;

import java.nio.ByteBuffer;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;

/**
 * @author Will Czifro
 */
public class SocketUnitTest {

    @Test
    public void testReceive_Success() {
        Socket sut = MockGenerator.mockSocket(3);
        if (sut == null)
            fail("Failed to setup sut");

        sut.setBufferSize(1024);

        byte[] data = sut.receive();

        assertThat(ByteBuffer.wrap(data).getInt() == 5000).isTrue();
    }

    @Test
    public void testReceiveAll_Success() {
        Socket sut = MockGenerator.mockSocket(1);
        if (sut == null)
            fail("Failed to setup sut");

        sut.setBufferSize(1024);

        String msg = new String(sut.receiveAll(5)).trim();

        assertThat(msg.equals("Hello"));
    }

    @Test
    public void testReceive_Throws_SocketStreamException() {
        Socket sut = MockGenerator.mockSocket(0);
        if (sut == null)
            fail("Failed to setup sut");

        sut.setBufferSize(1);

        assertThatThrownBy(() -> sut.receive())
                .isInstanceOf(SocketStreamException.class);
    }
}
