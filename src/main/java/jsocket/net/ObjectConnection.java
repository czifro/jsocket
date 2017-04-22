package jsocket.net;

import java.io.Closeable;

import jsocket.net.Connection;
import jsocket.util.ISerDe;
import jsocket.util.IConfigurator;

/**
 * Wraps a {@link Connection} to provide SerDe
 *   operations to incoming/outgoing raw data
 */
public class ObjectConnection implements Closeable {

    private Connection impl;
    private ISerDe serde;

    /**
     * @param conn raw socket
     */
    public ObjectConnection(Closeable conn) {
        this(Connection.getInstance(conn));
    }

    /**
     * @param conn connection
     */
    public ObjectConnection(Connection conn) {
        impl = conn;
    }

    /**
     * Deserializes and receives an object of specified type
     * @param type type to cast object to
     * @param <T> type parameter
     * @return
     */
    public <T> T receive(Class<T> type) {
        byte[] data = impl.receive();
        return serde.deserialize(data, type);
    }

    /**
     * Serializes and sends an object
     * @param t generic object
     * @param <T> type parameter
     */
    public <T> void send(T t) {
      byte[] data = serde.serialize(t);
      impl.send(data);
    }

    /**
     * Set the SerDe service used by this abstraction
     * @param serde SerDe service
     */
    public void setSerDe(ISerDe serde) {
      this.serde = serde;
    }

    /**
     * Configures connection
     * @param configurator connection configurator
     */
    public void configureConnection(IConfigurator configurator) {
        impl = configurator.configure(impl);
    }

    /**
     * Gets underlying implementation
     * @return implementation
     */
    public Connection getImpl() {
        return impl;
    }

    /**
     * Close connection
     */
    public void close() {
      impl.close();
    }

}