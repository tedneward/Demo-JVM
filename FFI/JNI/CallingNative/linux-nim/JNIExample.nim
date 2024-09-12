{.hint[XDeclaredButNotUsed]:off.}

type
    jobject_base {.inheritable, pure.} = object
    jobject* = ptr jobject_base

proc Java_JNIExample_sayHello(env: pointer, obj: jobject, len: int32): void {.exportc, cdecl, dynlib.} =
    echo "Hello world from Nim! Your string is length ", len, " !"
