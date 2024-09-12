use jni::JNIEnv;
use jni::objects::{JObject};

// JNIEXPORT void JNICALL Java_JNIExample_sayHello(JNIEnv *env, jobject object, jint len)
#[no_mangle]
pub extern "C" fn Java_JNIExample_sayHello(_env: JNIEnv, _object: JObject, len: u32) {
    println!("Hello from Rust!\nThe length of your string is {}.\n\n", len);
}

#[cfg(test)]
mod tests {
    #[test]
    fn it_works() {
        let result = 2 + 2;
        assert_eq!(result, 4);
    }
}
