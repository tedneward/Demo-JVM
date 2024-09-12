echo JAVA_HOME is currently $JAVA_HOME

echo Compiling Java \(and generating header, for what thats worth\)...
echo \(JDK 8 and earlier will not recognize that "-h" flag, and require running "javah JNIExample"\)
javac -h . -d . ../JNIExample.java

echo Compiling Rust code...
cargo build; cp target/debug/libJNIExample.so .
