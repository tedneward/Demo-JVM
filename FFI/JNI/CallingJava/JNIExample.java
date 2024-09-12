public class JNIExample {

    // Native method, no body.
    public native void sayHello(String message);
  
    public static void main (String args[]) {
      String str = "Hello, JNI world!";
      (new JNIExample()).sayHello(str);
    }
  
    // This loads the library at runtime.
    static {
      System.loadLibrary("jniexample");
    }
}
