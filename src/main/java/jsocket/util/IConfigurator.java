package jsocket.util;

import jsocket.net.Connection;



@FunctionalInterface
public interface IConfigurator {
    Connection configure(Connection conn);
}