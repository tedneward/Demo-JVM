#! /bin/bash

echo JAVA_HOME currently set to $JAVA_HOME

echo Compiling Main.java ...
javac -d . ../Main.java

echo Compiling Launcher...
g++ -I"$JAVA_HOME/include" -o Launcher ../Launcher.cpp $JAVA_HOME/libexec/lib/server/libjvm.so
