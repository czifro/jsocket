package jsocket.net;

import java.io.Closeable;

import jsocket.net.Connection;
import jsocket.util.ISerDe;
import jsocket.util.IConfigurator;

public class ObjectConnection implements Closeable {

    private Connection impl;
    private ISerDe serde;

    public ObjectConnection(Closeable conn) {
        this(Connection.getInstance(conn));
    }

    public ObjectConnection(Connection conn) {
        impl = conn;
    }

    public <T> T receive(Class<T> type) {
        byte[] data = impl.receive();
        return serde.deserialize(data, type);
    }

    public <T> void send(T t) {
      byte[] data = serde.serialize(t);
      impl.send(data);
    }

    public void setSerDe(ISerDe serde) {
      this.serde = serde;
    }

    public void configureConnection(IConfigurator configurator) {
        impl = configurator.configure(impl);
    }

    public void close() {
      impl.close();
    }

}