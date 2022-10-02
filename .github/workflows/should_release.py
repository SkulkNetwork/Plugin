#!/usr/bin/python
from project_info import get_info

with open("LATEST.txt", "w") as latest:
    version = latest.read()

if get_info("version") == version:
    exit(1)
