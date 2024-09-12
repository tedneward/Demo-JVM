public class JNIExample {

  // {{## BEGIN native-method ##}}
  // Native method, no body.
  public native void sayHello(int length);

  public static void main (String args[]) {
    String str = "Hello, world!";

    (new JNIExample()).sayHello(str.length());
  }
  // {{## END native-method ##}}

  // {{## BEGIN load ##}}
  static {
    System.out.println(System.mapLibraryName("jniexample"));
    System.loadLibrary("jniexample");
  }
  // {{## END load ##}}
}
