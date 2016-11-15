package jsocket.test.unit;

import jsocket.socket.StringSocket;
import jsocket.util.FilterFunctionType;
import jsocket.test.mock.MockGenerator;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;

/**
 * @author Will Czifro
 */
public class StringSocketUnitTest {

    @Test
    public void testReceiveString_Success() {
        StringSocket sut = MockGenerator.mockStringSocket(1);
        if (sut == null)
            fail("Failed to setup sut");

        sut.setBufferSize(1024);

        String msg = sut.receiveString().trim();

        assertThat(msg.equals("Hello World")).isTrue();
    }

    @Test
    public void testReceiveFixedString_Success() {
        StringSocket sut = MockGenerator.mockStringSocket(1);
        if (sut == null)
            fail("Failed to setup sut");

        sut.setBufferSize(1024);

        String msg = sut.receiveFixedString(5);

        assertThat(msg.equals("Hello")).isTrue();
    }

    @Test
    public void testReceiveString_WithFilter_Success() {
        StringSocket sut = MockGenerator.mockStringSocket(1);
        if (sut == null)
            fail("Failed to setup sut");

        sut.setBufferSize(1024);

        sut.setFilterFunction(FilterFunctionType.NULL_CHARS);
        sut.useFilterFunction(true);

        String msg = sut.receiveString();

        assertThat(msg.equals("Hello World")).isTrue();
    }
}
