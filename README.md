JSocket
===============
A Java Socket Programming Library

![Build Status](https://travis-ci.org/czifro-tech/jsocket.svg?branch=master)


Summary
----------

JSocket was developed to make socket programming in Java simpler. JSocket currently only focuses on TCP connections. What makes this library useful is that it makes cross-platform and cross-language TCP connections easier to deal with on the Java side. This library attempts to ensure clean messages come through that come from another platform/language. In some instances, say C# client -> Java server, a "dirty" message will come through. The Jsocket library can clean the message and return to a developer the correct message. This library includes various feature that can send anything from a simple string to a complex object. Features also include RSA encryption. Generate your own private and public keys.


How to Use
-----------

Link to tutorial: https://github.com/czifro/JSocket/wiki/Tutorial


Build Status & Download
------------------------

|v0.4.2 (master) |v0.5.0 (beta 1) |v0.5.0 (beta 2) |v0.5.0 (active)|
|:------:|:------:|:------:|:------:|
|[![](https://travis-ci.org/czifro-tech/jsocket.svg?branch=master)](https://travis-ci.org/czifro-tech/jsocket)|[![](https://travis-ci.org/czifro-tech/jsocket.svg?branch=vNext_0_5_0_beta_2)](https://travis-ci.org/czifro-tech/jsocket)|[![](https://travis-ci.org/czifro-tech/jsocket.svg?branch=vNext_0_5_0_beta_2)](https://travis-ci.org/czifro-tech/jsocket) | [![](https://travis-ci.org/czifro-tech/jsocket.svg?branch=vNext_0_5_0)](https://travis-ci.org/czifro-tech/jsocket)
|[Link](https://github.com/czifro-tech/jsocket/tree/mvn-repo/com/czifrodevelopment/jsocket/jsocket/0.4.2-SNAPSHOT) |[Link](https://www.dropbox.com/sh/2ea67dzctn4d0dl/AACXkmTNF9OOPbw3CHpKpONca?dl=0) |[Link](https://www.dropbox.com/sh/mfmd0sfei6qnxyp/AAC3Best69HwUjNrbXmk7QRea?dl=0) | ---- |


History: https://github.com/czifro/JSocket/wiki/Listed-Releases


Trello
-------------

Development can be tracked here: https://trello.com/b/6E8x9Mnv


===========


Credit
-------------

The ObjectSocket class is functional partly due to Google's [Gson package for Java](https://code.google.com/p/google-gson/). This package is great for converting any object to JSON and vice versa.

   
=====================


MIT License
---------------------------

The JSocket repository is released under the MIT License. Use it anyway you like. I release this software as is. In no way am I liable for anything as per outlined by the MIT License.

