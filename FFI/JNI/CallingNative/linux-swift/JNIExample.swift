// {{## BEGIN native_impl ##}}
// JNIEXPORT void JNICALL Java_JNIExample_sayHello(JNIEnv *env, jobject object, jint len)
@_cdecl("Java_JNIExample_sayHello")
public func JNIExample_sayHello(_ env: OpaquePointer, _ this: OpaquePointer, _ len: CInt) {
    print("Hello from Swift!")
    print("Your string was \(len) characters long")
}
// {{## END native_impl ##}}

//*
// {{## BEGIN onload ##}}
//JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved)
@_cdecl("JNI_OnLoad")
public func onLoad() -> CInt {
//public func onLoad(_ vm: OpaquePointer, _ reserved: OpaquePointer) -> CInt {
    print(">>> JNIExample native library loaded")
    return 65538  // JNI_VERSION_1_2 (from jni.h)
}
// {{## END onload ##}}

// {{## BEGIN onunload ##}}
//JNIEXPORT void JNICALL JNI_OnUnload(JavaVM* vm, void* reserved)
@_cdecl("JNI_OnUnload")
public func onUnload() {
//public func onUnload(_ vm: OpaquePointer, _ reserved: OpaquePointer) {
    print(">>> JNIExample native library unloaded")
}
// {{## END onunload ##}}
// */

// Compile with swiftc JNIExample.swift -emit-library -o libjniexample.so
