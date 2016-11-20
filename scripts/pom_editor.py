#!/usr/bin/env python
import sys
import os
import os.path
import xml.dom.minidom
import config

def __pom_path():
    return os.getcwd() + '/pom.xml'

def __get_pom():
    return xml.dom.minidom.parse(__pom_path())

def __add_id_and_url_to_node(pom, node, id, url):
    nodeId = pom.createElement("id")
    nodeUrl = pom.createElement("url")
    idNode = pom.createTextNode(id)
    urlNode = pom.createTextNode(url)
    nodeId.appendChild(idNode)
    nodeUrl.appendChild(urlNode)
    node.appendChild(nodeId)
    node.appendChild(nodeUrl)
    return pom, node

def __add_snapshot_to_pom(pom, node):
    snapshot = pom.createElement("snapshotRepository")
    pom, snapshot = __add_id_and_url_to_node(pom, snapshot, "snapshots", config.MAVEN_SERVER + config.SNAPSHOT_PATH)
    node.appendChild(snapshot)
    return pom, node

def __add_staging_to_pom(pom, node):
    staging = pom.createElement("repository")
    pom, staging = __add_id_and_url_to_node(pom, staging, "staging", config.MAVEN_SERVER + config.STAGING_PATH)
    node.appendChild(staging)
    return pom, node

def __add_release_to_pom(pom, node):
    release = pom.createElement("repository")
    pom, release = __add_id_and_url_to_node(pom, release, "release", config.MAVEN_SERVER + config.RELEASE_PATH)
    node.appendChild(release)
    return pom, node

def __write_pom(pom):
    pomStr = pom.toxml()

    f = open(__pom_path(), 'w')
    f.write(pomStr)
    f.close()

def add_repo_to_pom(repo_type):
    pom = __get_pom()
    project = pom.getElementsByTagName("project")[0]
    dists = project.getElementsByTagName("distributionManagement")
    if not dists:
        dist = pom.createElement("distributionManagement")
        project.appendChild(dist)
    else:
        dist = dists[0]

    if repo_type == config.REPO_TYPES[0]:
        pom, dist = __add_snapshot_to_pom(pom, dist)
    elif repo_type == config.REPO_TYPES[1]:
        pom, dist = __add_staging_to_pom(pom, dist)
    elif repo_type == config.REPO_TYPES[2]:
        pom, dist = __add_release_to_pom(pom, dist)
    else:
        print "Unknown repo_type: " + repo_type + ". Stopping deployment."
        sys.exit(1)

    __write_pom(pom)

def set_project_version(snapshot=False ,beta=False, beta_val=None):
    # if nothing is set, do nothing and return
    pom = __get_pom()

    project = pom.getElementsByTagName("project")[0]
    version = project.getElementsByTagName("version")[0]
    versionVal = version.firstChild.nodeValue
    if beta:
        versionVal += "-beta"
        if beta_val is not None:
            versionVal += "-" + str(beta_val)
    if snapshot:
        versionVal += "-" + config.REPO_TYPES[0]

    version.firstChild.replaceWholeText(versionVal)

    __write_pom(pom)
    return versionVal
