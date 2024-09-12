echo JAVA_HOME is currently $JAVA_HOME

echo Compiling Java \(and generating header\)...
echo \(JDK 8 and earlier will not recognize that "-h" flag, and require running "javah JNIExample"\)
javac -d . ../JNIExample.java


echo Compiling native Nim code...
nim c --header --app:lib JNIExample.nim
