@ECHO Building JNIExample and generating JNI header...
javac -h . -d . ..\JNIExample.java

@ECHO Building local X64 C implementation...
cl /I%JAVA_HOME%\include /I%JAVA_HOME%\include\win32 /LD JNIExample.cpp

