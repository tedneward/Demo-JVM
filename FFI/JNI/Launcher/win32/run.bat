@ECHO OFF

@ECHO Making sure jvm.dll is nearby...
copy %JAVA_HOME%/bin/server/jvm.dll .

@ECHO Running...
Launcher.exe