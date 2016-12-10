# *Datagram Tutorial

This class/interface is the base class/interface of the .datagram.* sub-package. `Datagram` is the base interface and `DatagramImpl` is the default implementation. This is a very bare bone class that can only send and receive bytes. The following is a break down of how to use the class/implementation and a full sample of using the code.

This class/interface is very similar to `Socket` and `SocketImpl`. Many of the methods are the same. However, with `Datagram`, there is the option to handle `java.net.DatagramPacket`s directly. This class/interface also allow for the `InetAddress` and `port` to be changed without creating a new `java.net.DatagramSocket`. Only one instance of this object needs to be created. What this means for servers is that packets can be sent back to the source of any received packet. Consider the following:

```java

Datagram dgram = new Datagram(new DatagramSocket(50000));

byte[] recv = dgram.receive();

// do something

byte[] data; // initialize this with something

// send data back to source
dgram.send(data, dgram.getReceivedInetAddress(), dgram.getReceivedPort());

dgram.close();

```

The port specified on line 1 is only the port `Datagram` is listening on. A port and address need to be provided for outgoing traffic. Since the subclasses/interfaces are so similar to those in .socket.*, there will not be tutorials on them.