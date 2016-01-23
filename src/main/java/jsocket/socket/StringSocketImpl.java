package jsocket.socket;

import jsocket.util.FilterFunctionType;

import java.net.*;
import java.util.function.Function;

/**
 * @author Will Czifro
 */
public class StringSocketImpl extends SocketImpl implements StringSocket {

    private Function<String, String> filter;
    private boolean useFunc;

    public StringSocketImpl(java.net.Socket conn) {
        super(conn);
    }

    public String receiveString() {
        return receiveFixedString(getBufferSize());
    }

    public String receiveFixedString(int length) {
        byte[] data;
        if (connectionIsEncrypted())
            data = receiveEncryptedAll(length);
        else
            data = receiveAll(length);

        return buildString(data);
    }

    public void sendString(String str) {
        if (connectionIsEncrypted())
            sendEncrypted(str.getBytes());
        else
            send(str.getBytes());
    }

    public void setFilterFunction(FilterFunctionType type) {
        filter = type.getFunc();
    }

    public void setFilterFunction(Function<String, String> func) {
        filter = func;
    }

    public void useFilterFunction(boolean useFunc) {
        this.useFunc = useFunc;
    }

    private String buildString(byte[] data) {
        String msg = new String(data);
        return runFilterOnString(msg);
    }

    private String runFilterOnString(String str) {
        if (!useFunc) return str;
        return filter.apply(str);
    }
}
