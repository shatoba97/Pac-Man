#!/bin/bash
javac -d bin/ -sourcepath ./src/ -classpath ./lib/gson-2.6.2.jar ./src/pacman/bot/PacManSearchBotV2.java
jar cvfm bot.jar MBOT.MF -C ./bin .
