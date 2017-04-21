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

    public String receive() {
        byte[] data = impl.receive();
        return buildString(data);
    }

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

    public void configureConnection(IConfigurator configurator) {
        impl = configurator.configure(impl);
    }

    public Connection getImpl() {
        return impl;
    }

    public void close() throws IOException {
        impl.close();
    }

    /**
     * Converts a byte array to a string
     * @param data the byte array to be converted to a string
     * @return a string
     */
    private String buildString(byte[] data) {
        String msg = new String(data);
        return runFilterOnString(msg);
    }

    /**
     * Uses the set filter and processes the string
     * If the useFunc flag is not set, the string is just returned
     * @param str the string to be processed
     * @return the processed string
     */
    private String runFilterOnString(String str) {
        if (!useFunc) return str;
        return filter.filter(str);
    }
}
