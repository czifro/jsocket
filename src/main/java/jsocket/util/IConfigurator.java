package jsocket.util;

import jsocket.net.Connection;


/**
 * Functional Interface for configuring a connection
 */
@FunctionalInterface
public interface IConfigurator {
    Connection configure(Connection conn);
}