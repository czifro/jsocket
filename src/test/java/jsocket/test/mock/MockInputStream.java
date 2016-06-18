package jsocket.test.mock;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * This is a mocked InputStream.
 * This is to test specific features of each socket type
 * @author Will Czifro
 */
public class MockInputStream extends InputStream {

    private byte[] data;
    private int index = 0;
    private int op;

    public MockInputStream(int op) {
        switch (op) {
            case 1:
                data = ("Hello World").getBytes();
                break;
            case 2:
                data = ("{\"Name\": \"Will Czifro\"}").getBytes();
                break;
            case 3:
                data = ByteBuffer.allocate(4).putInt(5000).array();
                break;
        }
        this.op = op;
    }

    public int read() throws IOException {
        if (op == 0) throw new IOException();
        if (index >= data.length) return -1;
        return data[index++];
    }
}
