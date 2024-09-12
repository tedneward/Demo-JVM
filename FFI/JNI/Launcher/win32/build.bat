@ECHO JAVA_HOME is currently set to %JAVA_HOME%

@ECHO Compiling Main.java
javac -d . ..\Main.java

cl -I%JAVA_HOME%\include -I%JAVA_HOME%\include\win32 -MD ..\Launcher.cpp %JAVA_HOME%\lib\jvm.lib
@ECHO Make sure jvm.dll is on the PATH when running!
