echo JAVA_HOME is currently $JAVA_HOME

echo Compiling Java \(and generating header\)...
echo \(JDK 8 and earlier will not recognize that "-h" flag, and require running "javah JNIExample"\)
javac -h . -d . ../JNIExample.java


echo Compiling native C++ code...
gcc -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/darwin/" -o libjniexample.jnilib -shared JNIExample.cpp
#echo Compiling native Swift code...
#swiftc JNIExample.swift -emit-library -o libjniexample.so
