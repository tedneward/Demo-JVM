#!/bin/bash
echo JAVA_HOME currently set to $JAVA_HOME

echo Compiling Main.java ...
javac -d . ../Main.java

echo Compiling Launcher...
gcc -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/darwin/" -o Launcher ../Launcher.cpp $JAVA_HOME/lib/server/libjvm.dylib

