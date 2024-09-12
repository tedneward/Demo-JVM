echo JAVA_HOME is currently $JAVA_HOME

echo Compiling Java \(and generating header\)...
echo \(JDK 8 and earlier will not recognize that "-h" flag, and require running "javah JNIExample"\)
javac -h . -d . ../JNIExample.java


echo Compiling native C code...
g++ -std=c++11 -shared -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/linux" JNIExample.c -o libjniexample.so
#echo Compiling native Swift code...
#swiftc JNIExample.swift -emit-library -o libjniexample.so
