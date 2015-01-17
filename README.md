JSocket
===============

jar file link: https://www.dropbox.com/s/hrkwv02l7b3bvop/jsocket.jar?dl=0

JSocket wraps a java.net.Socket to easily read and write data to the stream.
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
  - ObjectSocket (Inherits from MessageSocket)
   
MessageSocket is a class that wraps a Socket object and sends and receives strings, sample code:

      java.net.ServerSocket server = new ServerSocket(port); // Used to accept connections
      java.net.Socket conn = server.accept();                // Accepts a TCP Socket connection
      MessageSocket sock = new MessageSocket(conn);          // Wraps around Socket
  
      sock.send_msg("Hello World!");     // Pass a string as argument, string is written to stream and sent
  
      String msg = sock.recv_msg();      // Receives a message, buffer size is limited
  
      String wholeMsg = sock.recv_all_msg(size);  // If message is large, use this to set new buffer size
      
ObjectSocket is a class that wraps a Socket object and sends and receives objects, converts objects to and from JSON.
Sample code:

      java.net.ServerSocket server = new ServerSocket(port); // Used to accept connections
      java.net.Socket conn = server.accept();                // Accepts a TCP Socket connection
      ObjectSocket sock = new ObjectSocket(conn);            // Wraps around Socket
      
      Person p = new Person("William", 22);
      
      sock.send_object(p, Person.class);           // Object will be converted to a JSON String and sent
      
      Person p2 = sock.recv_object(Person.class);  // Specify class type and it will return an object of that type
      
      String json = sock.recv_object_asString();   // Returns the JSON that is received
      
      String failedJson = sock.recover_failed_json() // Should parsing the received JSON fail, you can recover it
