JSocket
===============


Summary
----------

JSocket wraps a java.net.Socket to easily read and write data to the stream.

      // Use this:
      java.net.ServerSocket server = new ServerSocket(port); // Used to accept connections
      java.net.Socket conn = server.accept();                // Accepts a TCP Socket connection
      // Or this:
      java.net.Socket conn = new Socket("ipaddress", port);  // Connect to a TCP server
      // to instantiate Socket object and wrap with following classes


The JSocket class is a super class with basic functions of reading and writing bytes on the stream
sample code:

      JSocket sock = new JSocket(conn);                      // Wraps around Socket
      
      sock.send(object.getBytes());     // Pass a byte[] as argument
      
      byte[] fixedBytes = sock.recv();  // Receives bytes, buffer size is limited
      
      byte[] allBytes = sock.recv_all(size);  // Receives bytes, buffer size is dynamic
      
      sock.close();  // Closes Socket and I/O stream objects, inherited by all subclasses

Current list of subclasses:

- MessageSocket
  - ObjectSocket (Inherits from MessageSocket)
  - FileTransferSocket (Inherits from MessageSocket)
   
MessageSocket is a class that wraps a Socket object and sends and receives strings, sample code:

      MessageSocket sock = new MessageSocket(conn);          // Wraps around Socket
  
      sock.send_msg("Hello World!");     // Pass a string as argument, string is written to stream and sent
  
      String msg = sock.recv_msg();      // Receives a message, buffer size is limited
  
      String wholeMsg = sock.recv_all_msg(size);  // If message is large, use this to set new buffer size
      
ObjectSocket is a class that wraps a Socket object and sends and receives objects, converts objects to and from JSON.
Sample code:

      ObjectSocket sock = new ObjectSocket(conn);            // Wraps around Socket
      
      Person p = new Person("William", 22);
      
      sock.send_object(p, Person.class);           // Object will be converted to a JSON String and sent
      
      Person p2 = sock.recv_object(Person.class);  // Specify class type and it will return an object of that type
      
      String json = sock.recv_object_asString();   // Returns the JSON that is received
      
      String failedJson = sock.recover_failed_json() // Should parsing the received JSON fail, you can recover it

FileTransferSocket is a class that is designed to send and receive files over a Socket connection. File can be of any format and of arbitrary size.

      FileTransferSocket sock = new FileTransferSocket(conn);     // Wraps around Socket
      
      File f = new File("/Users/username/Desktop/Person.java");  // File to be sent
      
      sock.send_file(f);           // Object will be converted to a JSON String and sent
      
      File rF = sock.recv_file(path);  // Specify path to save file as String and it will write the file to that location

===============


Build History
--------------

Please use following link: https://github.com/czifro/JSocket/wiki/Listed-Releases
   
=====================


Download
-----------

Link to builds: https://www.dropbox.com/sh/kz0lbcw93r03tfx/AADt_1Jc8MImNdgpZQyF2u9Ia?dl=0
