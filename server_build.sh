#!/bin/bash
mkdir bin
javac -d ./bin -sourcepath ./src -classpath ./lib/gson-2.6.2.jar ./src/pacman/server/Server.java
jar cvfm server.jar MSERVER.MF -C ./bin .
