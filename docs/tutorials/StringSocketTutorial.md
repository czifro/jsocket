# StringSocket Tutorial

This class/interface is a member of the .socket.* sub-package. `StringSocket` is the interface and `StringSocketImpl` is the default implementation. Both inherit from `Socket` and `SocketImpl`, respectively. This adds a layer of abstraction to the base class/interface to handle strings on the user's behalf. The following is a break down of how to use the class/interface and a full sample of using the code.

### Instantiation

`StringSocketImpl` has a constructor that takes a single argument; a `java.net.Socket` object. The constructor makes a call to the super constructor. Look at the documentation for `jsocket.socket.Socket` to see what that entails. 

    StringSocket ssock = new StringSocketImpl(conn);
    
### Sending

Sending a string is really simple. It only requires a single method call that takes a `String` as an argument. Consider the following:

    ssock.sendString("Hello World");

### Receiving

Receiving data is also rather simple. the `receiveString()` method will return a `String`. The following is an illustration:

    String msg = ssock.receiveString();

You can also receive a string of a specific length:

    String msg = ssock.receiveFixedString(512);
    
### Filtering

You can also apply filters to strip certain characters. This is especially helpful when the character encoding is in conflict due to cross-platform usage. For instance, if null characters end up mixed in the string, a filter can be applied to eliminate these characters using the following:

    ssock.setFilterFunction(FilterFunctionType.NULL_CHARS);
    ssock.useFilterFunction(true);
    
You must also set the flag to use it. This is to keep the user from being forced to always use the filter function. You can also use a custom function via the following:

    // Java 7 and earlier
    ssock.setFilterFunction(new Function<String, String>(){/*Function Implementation*/});
    
    // Java 8+
    ssock.setFilterFunction((s) -> {/*Function Implementation*/});
    

### Closing

Uses implementation of `jsocket.socket.Socket`.
    
### Exceptions

All exceptions in this class are inherited from `jsocket.socket.Socket`.

### Full Sample

    // Wrap a java.net.Socket object with an instance of jsocket.socket.StringSocket
    StringSocket ssock = new StringSocketImpl(conn);
    
    // Set size of buffer for receiving data, defined in jsocket.socket.Socket
    ssock.setBufferSize(1024); // 1024 bytes will be read from stream
    
    // Apply filter to incoming messages
    ssock.setFilterFunction(FilterFunctionType.NULL_CHARS);
    ssock.useFilterFunction(true);
    
    // Send data via simple method call
    ssock.sendString("Hello World");
    
    // Receive data via simple method call
    String msg = ssock.receiveString();
    
    // Close connection, defined in jsocket.socket.Socket
    ssock.close();