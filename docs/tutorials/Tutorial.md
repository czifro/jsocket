# Tutorial#

This is the first step in using any one of the classes in the .socket.* package:

    // Use this:
    java.net.ServerSocket server = new ServerSocket(port); // Used to accept connections
    java.net.Socket conn = server.accept();                // Accepts a TCP Socket connection
    // Or this:
    java.net.Socket conn = new Socket("ipaddress", port);  // Connect to a TCP server


Available tutorials:

   * [Socket and SocketImpl](SocketTutorial.md)
   * [StringSocket and StringSocketImpl](StringSocketTutorial.md)
   * [ObjectSocket and ObjectSocketImpl](ObjectSocketTutorial.md)
   * [Crypto](CryptoTutorial.md)
   * [Exceptions](ExceptionsTutorial.md)

