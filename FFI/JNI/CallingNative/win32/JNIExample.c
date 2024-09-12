#include <jni.h>
#include "JNIExample.h"
#include <stdio.h>

JNIEXPORT void JNICALL Java_JNIExample_sayHello(JNIEnv *env, jobject object, jint len) {
  printf("Hello from C/C++!\nThe length of your string is %d.\n\n", len);
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
  printf(">>> JNIExample shared library loaded!\n");
  return JNI_VERSION_1_2;
}
JNIEXPORT void JNICALL JNI_OnUnload(JavaVM* vm, void *reserved) {
  printf("<<< JNIExample shared library unloaded!\n");
}