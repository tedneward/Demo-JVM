// {{## BEGIN headers ##}}
#include <jni.h>       /* where everything is defined */
#include <jni_md.h>

#include <assert.h>
// {{## END headers ##}}

// {{## BEGIN launch-prep ##}}
#define MAX_OPTS 4
int main(int argc, char* argv[]) 
{
    JavaVM *jvm;       /* denotes a Java VM */
    JNIEnv *env;       /* pointer to native method interface */
    
    JavaVMInitArgs vm_args; /* JDK/JRE 6 VM initialization arguments */

    JavaVMOption options[MAX_OPTS]; int n = 0;
    options[n++].optionString = "-Djava.class.path=.";
        // gcc warning: conversion from string literal to 'char *' is deprecated [-Wc++11-compat-deprecated-writable-strings]

    assert(n < MAX_OPTS);
// {{## END launch-prep ##}}

// {{## BEGIN launch ##}}
    vm_args.version = JNI_VERSION_1_6;
    vm_args.nOptions = n;
    vm_args.options = (JavaVMOption*)&options;
    vm_args.ignoreUnrecognized = true;

    /* load and initialize a Java VM, return a JNI interface pointer in env */
    JNI_CreateJavaVM(&jvm, (void**)&env, &vm_args);
// {{## END launch ##}}

// {{## BEGIN call-java ##}}
    /* invoke the Main.test method using the JNI */
    jclass cls = env->FindClass("Main");
    jmethodID mid = env->GetStaticMethodID(cls, "test", "()V");
    env->CallStaticVoidMethod(cls, mid);
// {{## END call-java ##}}

// {{## BEGIN cleanup ##}}
    /* We are done. */
    jvm->DestroyJavaVM();
// {{## END cleanup ##}}
}
