# Socket Tutorial

This class/interface is the base class/interface of the .socket.* sub-package. `Socket` is the base interface and `SocketImpl` is the default implementation. This is a very bare bone class that can only send and receive bytes. The following is a break down of how to use the class/implementation and a full sample of using the code.

### Instantiation

`SocketImpl` has a constructor that takes a single argument; a `java.net.Socket` object. The constructor will do the appropriate setup to read and write data to the streams. The default implementation uses `java.io.DataInputStream` and `java.io.DataOutputStream` to handle reading and writing date from/to the stream, respectively.

    Socket sock = new SocketImpl(conn);
    
 If an exception occurs while the streams are being opened, an attempt will be made to try and close the connection, then a runtime exception is thrown: `jsocket.exceptions.InstantiationException`.

### Setting Buffer

Before you can start using this new object, you need to specify the size of the buffer for reading in bytes. The following method call will set the buffer size:

    sock.setBufferSize(1024);
    
### Sending

Sending data is really simple. It only requires a single method call that takes a `byte[]` as an argument. Consider the following:

    sock.send(("Hello World").getBytes());

### Receiving

Receiving data is also rather simple. the `receive()` method will return a `byte[]`. The following is an illustration:

    byte[] data = sock.receive();

You can also receive a specified number of bytes using `receiveAll(int size)`:

    byte[] data = sock.receiveAll(512);

### Closing

A single method call will close the streams and socket connection. For example:

    sock.close();
    
### Exceptions

Sending, receiving, and closing all throw the same exception at runtime. If for some reason a stream fails to perform, a `jsocket.exceptions.SocketStreamException` will be thrown. The constructor could potentially throw this exception as well since it attempts to close if instantiation fails.

### Full Sample

    // Wrap a java.net.Socket object with an instance of jsocket.socket.Socket
    Socket sock = new SocketImpl(conn);
    
    // Set size of buffer for receiving data
    sock.setBufferSize(1024); // 1024 bytes will be read from stream
    
    // Send data via simple method call
    sock.send(("Hello World").getBytes());
    
    // Receive data via simple method call
    byte[] data = sock.receive();
    
    // Close connection
    sock.close();