#!/usr/bin/python
from sys import argv as args
from xmltodict import parse as xml_parse

with open("pom.xml") as pom:
    pom = xml_parse(pom.read())

if __name__ == "__main__":
    print(pom["project"][args[1]])
else:
    get_info = lambda param: pom["project"][param]
