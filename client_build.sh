#!/bin/bash
javac -d ./bin -sourcepath ./src -classpath ./lib/gson-2.6.2.jar ./src/Main.java
jar cvfm client.jar MCLIENT.MF -C ./bin .
