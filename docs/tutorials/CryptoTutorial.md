# Crypto Tutorial

This interface/class is a service external to the classes in .socket.* that can encrypt and decrypt data using either AES or RSA algorithms. This class has been made external to allow this feature to be optional. `Crypto` has two subclasses named `RSA` and `AES`. This service has provided an easy way to create a `Crypto` service using either algorithm with the following static method call:

    Crypto crypto = Crypto.newInstance(Crypto.DEFAULT_RSA); // Crypto.DEFAULT_AES for AES algorithm.
    
Look at the documentation for mor information on how to use it.