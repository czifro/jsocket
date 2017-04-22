package jsocket.net;

import java.io.Closeable;
import java.io.IOException;

import jsocket.util.FilterType;
import jsocket.util.IFilter;


import jsocket.util.IConfigurator;

/**
 * @author Will Czifro
 */
public class StringConnection implements Closeable {

    private Connection impl;
    private IFilter filter;
    private boolean useFunc;

    public StringConnection(Closeable conn) {
        this(Connection.getInstance(conn));
    }

    public StringConnection(Connection conn) {
        impl = conn;
    }

    /**
     * Receives and converts to string
     * @return received string
     */
    public String receive() {
        byte[] data = impl.receive();
        return buildString(data);
    }

    /**
     * Converts to bytes and sends string
     * @param str string to send
     */
    public void send(String str) {
        impl.send(str.getBytes());
    }

    /**
     * A function can be used to process string before returning it
     * in some cases, null characters are mixed in a string
     * A filter function can be used to remove null characters
     * @param type type of filter function
     */
    public void setFilterFunction(FilterType type) {
        filter = type.getFunc();
    }

    /**
     * A function can be used to process string before returning it
     * in some cases, null characters are mixed in a string
     * A filter function can be used to remove null characters
     * @param func filter function
     */
    public void setFilter(IFilter func) {
        filter = func;
    }

    /**
     * Toggles whether or not a filter function will be applied to received strings
     * @param useFunc toggle
     */
    public void useFilter(boolean useFunc) {
        this.useFunc = useFunc;
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

    private String buildString(byte[] data) {
        String msg = new String(data);
        return runFilterOnString(msg);
    }

    private String runFilterOnString(String str) {
        if (!useFunc) return str;
        return filter.filter(str);
    }
}
