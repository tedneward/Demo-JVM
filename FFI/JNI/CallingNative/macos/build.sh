#!/bin/bash

echo JAVA_HOME is currently $JAVA_HOME

echo Compiling Java \(and generating header\)...
echo \(JDK 8 and earlier will not recognize that "-h" flag, and require running "javah JNIExample"\)
javac -h . -d . ../JNIExample.java


#echo Compiling native C code...
#gcc -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/darwin/" -o libjniexample.jnilib -shared JNIExample.c
echo Compiling native Swift code...
# x86_64 (default)
swiftc JNIExample.swift -emit-library -o libjniexample.jnilib
# Uncomment for ARM/M1 -- not quite working yet, not sure why
#swiftc JNIExample.swift -target-cpu apple-m1 -emit-library -o libjniexample.jnilib

echo Make sure to run with explicit "-Djava.library.path=." property.
