#!/usr/bin/env python
import sys
import os
import os.path
import xml.dom.minidom

if os.environ["TRAVIS_SECURE_ENV_VARS"] == "false":
    print "no secure env vars available, skipping deployment"
    sys.exit()

homedir = os.path.expanduser("~")

deployDest = "";

if os.environ["TRAVIS_BRANCH"] == "master":
    deployDest = "releases"
elif os.environ["TRAVIS_BRANCH"] == "develop":
    deployDest = "staging"
elif "vNext" in os.environ["TRAVIS_BRANCH"]:
    deployDest = "snapshots"
else:
    print "not on develop or master or vNext, skipping deployment"
    sys.exit()

m2 = xml.dom.minidom.parse(homedir + '/.m2/settings.xml')
settings = m2.getElementsByTagName("settings")[0]

serversNodes = settings.getElementsByTagName("servers")
if not serversNodes:
    serversNode = m2.createElement("servers")
    settings.appendChild(serversNode)
else:
    serversNode = serversNodes[0]

sonatypeServerNode = m2.createElement("server")
sonatypeServerId = m2.createElement("id")
sonatypeServerUser = m2.createElement("username")
sonatypeServerPass = m2.createElement("password")

idNode = m2.createTextNode(deployDest)
userNode = m2.createTextNode(os.environ["JSOCKET_USERNAME"])
passNode = m2.createTextNode(os.environ["JSOCKET_PASSWORD"])

sonatypeServerId.appendChild(idNode)
sonatypeServerUser.appendChild(userNode)
sonatypeServerPass.appendChild(passNode)

sonatypeServerNode.appendChild(sonatypeServerId)
sonatypeServerNode.appendChild(sonatypeServerUser)
sonatypeServerNode.appendChild(sonatypeServerPass)

serversNode.appendChild(sonatypeServerNode)

m2Str = m2.toxml()
f = open(homedir + '/.m2/settings.xml', 'w')
f.write(m2Str)
f.close()