#!/bin/bash

echo JAVA_HOME is currently $JAVA_HOME

echo Compiling Java...
javac -d . ../JNIExample.java


echo Compiling native Swift code...
# x86_64 (default)
swiftc JNIExample.swift -emit-library -o libjniexample.so
# Uncomment for ARM/M1 -- not quite working yet, not sure why
#swiftc JNIExample.swift -target-cpu apple-m1 -emit-library -o libjniexample.jnilib

echo Make sure to run with explicit "-Djava.library.path=." property.
