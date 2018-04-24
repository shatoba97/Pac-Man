#!/bin/bash
javac -d ./bin -sourcepath ./src -classpath ./lib/gson-2.6.2.jar ./src/pacman/api/PacManAPI.java
jar cvfm api.jar MCLIENT.MF -C ./bin/pacman/api .