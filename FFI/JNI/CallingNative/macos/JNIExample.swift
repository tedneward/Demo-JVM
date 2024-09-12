import AppKit

//: Let's do a little Cocoa GUI display
func messageBox(title: String, message: String) -> Bool {
    let alert = NSAlert()
    alert.messageText = title
    alert.informativeText = message
    alert.addButton(withTitle: "OK")
    return alert.runModal() == NSApplication.ModalResponse.alertFirstButtonReturn
}
    //let _ = messageBox(title: "Hello from Swift!", message: "Your string was \(len) characters long")
    //  Doing the above will generate an exception, however:
    //  "*** Terminating app due to uncaught exception 'NSInternalInconsistencyException', 
    //   reason: 'NSWindow drag regions should only be invalidated on the Main Thread!'"
    //  Need to push the messageBox call onto a UI thread

// {{## BEGIN native_impl ##}}
// JNIEXPORT void JNICALL Java_JNIExample_sayHello(JNIEnv *env, jobject object, jint len)
@_cdecl("Java_JNIExample_sayHello")
public func JNIExample_sayHello(_ env: OpaquePointer, _ this: OpaquePointer, _ len: CInt) {
    print("Hello from Swift!")
    print("Your string was \(len) characters long")
}
// {{## END native_impl ##}}

/*
// {{## BEGIN onload ##}}
@_cdecl("JNI_OnLoad")
public func onLoad() -> CInt {
    print(">>> JNIExample native library loaded")
    return 65538  // JNI_VERSION_1_2 (from jni.h)
}
// {{## END onload ##}}

// {{## BEGIN onunloadload ##}}
@_cdecl("JNI_OnUnload")
public func onUnload() {
    print(">>> JNIExample native library unloaded")
}
// {{## END onload ##}}
*/

// Compile with swiftc JNIExample.swift -emit-library -o libjniexample.jnilib
