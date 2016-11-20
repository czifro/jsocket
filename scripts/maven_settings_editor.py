#!/usr/bin/env python
import sys
import os
import os.path
import xml.dom.minidom

def __home_dir():
    return os.path.expanduser("~")

def settings_path():
    return __home_dir() + "/.m2/settings.xml"

def setup_settings(repo_type, user, pwd):
    doc = xml.dom.minidom.parse(settings_path())
    settings = doc.getElementsByTagName("settings")[0]

    servers = settings.getElementsByTagName("servers")
    if not servers:
        servers = doc.createElement("servers")
        settings.appendChild(servers)
    else:
        servers = servers[0]

    server = doc.createElement("server")
    serverId = doc.createElement("id")
    serverUser = doc.createElement("user")
    serverPass = doc.createElement("password")

    id = doc.createTextNode(repo_type)
    _user = doc.createTextNode(user)
    _pwd = doc.createTextNode(pwd)

    serverId.appendChild(id)
    serverUser.appendChild(_user)
    serverPass.appendChild(_pwd)

    server.appendChild(serverId)
    server.appendChild(serverUser)
    server.appendChild(serverPass)

    servers.appendChild(server)

    docStr = doc.toxml()
    f = open(settings_path(), 'w')
    f.write(docStr)
    f.close()