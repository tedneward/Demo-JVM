#include <jni.h>
#include "JNIExample.h"
#include <stdio.h>

JNIEXPORT void JNICALL Java_JNIExample_sayHello(JNIEnv *env, jobject object, jstring message) {
  const char* native_message = env->GetStringUTFChars(message, NULL);

  int len = 0;
  // Call String.length() method on message
  jclass clsString = env->GetObjectClass(message);
  jmethodID midLength = env->GetMethodID(clsString, "length", "()I");
  if (midLength == 0)
    printf("Could not find length() method\n");
  len = env->CallIntMethod(message, midLength);

  printf("%s\nThe length of your string is %d.\n\n", native_message, len);

  env->ReleaseStringUTFChars(message, native_message);
}
