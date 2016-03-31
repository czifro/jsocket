package jsocket.test.unit;

import jsocket.exceptions.MalformedJsonException;
import jsocket.socket.ObjectSocket;

import static org.assertj.core.api.Assertions.*;

import jsocket.test.mock.MockGenerator;
import jsocket.test.mock.MockPerson;
import jsocket.util.FilterFunctionType;
import jsocket.util.JsonService;
import jsocket.util.JsonServiceImpl;
import org.junit.Test;

/**
 * @author Will Czifro
 */
public class ObjectSocketUnitTest {

    @Test
    public void testReceiveObject_Success() {
        ObjectSocket sut = MockGenerator.mockObjectSocket(2);
        if (sut == null)
            fail("Failed to setup sut");

        JsonService jsonService = new JsonServiceImpl();
        sut.setJsonTool(jsonService);

        sut.setBufferSize(1024);
        sut.setFilterFunction(FilterFunctionType.NULL_CHARS);
        sut.useFilterFunction(true);

        MockPerson person = sut.receiveObject(MockPerson.class);

        assertThat(person.Name.equals("Will Czifro")).isTrue();
    }

    @Test
    public void testReceiveObject_Throws_MalformedJsonException() {
        ObjectSocket sut = MockGenerator.mockObjectSocket(2);
        if (sut == null)
            fail("Failed to setup sut");

        JsonService jsonService = new JsonServiceImpl();
        sut.setJsonTool(jsonService);

        sut.setBufferSize(1024);

        assertThatThrownBy(() -> sut.receiveObject(MockPerson.class, 5))
                .isInstanceOf(MalformedJsonException.class);
    }
}
