JSocket
===============

JSocket wraps a java.net.Socket to easily read and write data to the stream
The JSocket class is an abstract class

Current list of subclasses:

- MessageSocket
   
MessageSocket is a class that can send a string using the TCP connection, sample code:

    java.net.ServerSocket server = new ServerSocket(port); // Used to accept connections
    java.net.Socket conn = server.accept();                // Accepts a TCP Socket connection
    MessageSocket sock = new MessageSocket(conn);          // Wraps around Socket
  
    sock.send_msg("Hello World!");     // Pass a string as argument, string is written to stream and sent
  
    String msg = sock.recv_msg();      // Receives a message, buffer size is limitted
  
    String wholeMsg = sock.recv_all_msg(size);  // If message is large, use this to set new buffer size
