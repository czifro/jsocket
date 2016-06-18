# ObjectSocket Tutorial

This class/interface is a member of the .socket.* sub-package. `ObjectSocket` is the interface and `ObjectSocketImpl` is the default implementation. Both inherit from `StringSocket` and `StringSocketImpl`, respectively. This adds a layer of abstraction to the base class/interface to handle objects on the user's behalf. This library uses JSON to exchange objects of a connection. A separate service, located in sub-package .util.*, called JsonService, must be setup and for this class to internally serialize and deserialize an object. The following is a break down of how to use the class/interface and a full sample of using the code.

### Instantiation

`ObjectSocketImpl` has a constructor that takes a single argument; a `java.net.Socket` object. The constructor makes a call to the super constructor. Look at the documentation for `jsocket.socket.StringSocket` to see what that entails. 

    ObjectSocket osock = new ObjectSocketImpl(conn);

### SerDe JSON Service

`ObjectSocket` can internally perform serialization and deserialization operations on incoming and out going data. This service has been externalized to allow custom implementations to be used for SerDe operations. `ObjectSocket` socket uses `jsocket.util.JsonService` to do SerDe. Look to the documentation on `jsocket.util.JsonService` for more details on this service.

    osock.setJsonService(JsonService.defaultImpl());

### Sending

Sending a string is really simple. It only requires a single method call that takes an object as an argument. Consider the following:

    osock.sendObject(new Person("Joe", 42));

### Receiving

Receiving data is also rather simple. the `receiveObject(Class<T> clazz)` method will return a type `T` object. The following is an illustration:

    Person p = osock.receiveObject(Person.class);

If you know the number of bytes needed for an object, you can specify the desired buffer size for a single receive call using `receiveObject(Class<T> type, int size)`:

    Person = osock.receiveObject(Person.class, 512);
    
### Filtering

If you use the preset buffer for receiving an object rather than using a specific buffer size for the incoming object, it is suggested that you utilize the null character filter to strip off the null characters that are remaining when the object does not fill the whole buffer. Look at the documentation on `jsocket.socket.StringSocket` for more information.
    
### Closing

Uses implementation of `jsocket.socket.Socket`.
    
### Exceptions

All exceptions found in `jsocket.socket.Socket` are inherited. If the JSON service has not been set, a `NullPointerException` will be thrown. If you use the default implementation of `JsonService`, look at that documentation to see what exceptions it throws. 

### Full Sample

    // Wrap a java.net.Socket object with an instance of jsocket.socket.StringSocket
    ObjectSocket osock = new ObjectSocketImpl(conn);
    
    // Set size of buffer for receiving data, defined in jsocket.socket.Socket
    osock.setBufferSize(1024); // 1024 bytes will be read from stream
    
    // Apply filter to incoming messages
    osock.setFilterFunction(FilterFunctionType.NULL_CHARS);
    osock.useFilterFunction(true);
    
    // Set JSON service
    osock.setJsonService(JsonService.defaultImpl());
    
    // Send data via simple method call
    osock.sendObject(new Person("Joe", 42));
    
    // Receive data via simple method call
    Person p = osock.receiveObject(Person.class);
    
    // Close connection, defined in jsocket.socket.Socket
    osock.close();