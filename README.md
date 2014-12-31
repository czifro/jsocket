JSocket
===============

JSocket wraps a java.net.Socket to easily read and write data to the stream
The JSocket class is a super class with basic functions of reading and writing bytes on the stream

example code:

      java.net.ServerSocket server = new ServerSocket(port); // Used to accept connections
      java.net.Socket conn = server.accept();                // Accepts a TCP Socket connection
      JSocket sock = new JSocket(conn);                      // Wraps around Socket
      
      sock.send(object.getBytes());     // Pass a byte[] as argument
      
      byte[] fixedBytes = sock.recv();  // Receives bytes, buffer size is limited
      
      byte[] allBytes = sock.recv_all(size);  // Receives bytes, buffer size is dynamic

Current list of subclasses:

- MessageSocket
   
MessageSocket is a class wraps a Socket object and sends and receives strings, sample code:

      java.net.ServerSocket server = new ServerSocket(port); // Used to accept connections
      java.net.Socket conn = server.accept();                // Accepts a TCP Socket connection
      MessageSocket sock = new MessageSocket(conn);          // Wraps around Socket
  
      sock.send_msg("Hello World!");     // Pass a string as argument, string is written to stream and sent
  
      String msg = sock.recv_msg();      // Receives a message, buffer size is limited
  
      String wholeMsg = sock.recv_all_msg(size);  // If message is large, use this to set new buffer size
