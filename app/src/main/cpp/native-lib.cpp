#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_xxl_example_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject jobj) {
    std::string hello = "Hello from C++";

    //3根据jobject 获取jclass
    jclass jclaz = env->GetObjectClass(jobj);

    const char* name = "add";
    //2.获取方法ID   (IF)V int float 参数，返回值为void 方法名为add
    jmethodID methodId= env->GetMethodID(jclaz,name,"(IF)V");

    // 1. c 调用Java方法 并传递参数
    env->CallVoidMethod(jobj,methodId,12,14.0);

    const char* clazz_name = "com/xxl/example/MainActivity";

    //调用Java static 方法
    //3 FindClass
    jclass static_clazz = env->FindClass(clazz_name);
    const char* function_sub_name = "sub";
    //2
    jmethodID sub_method_id = env->GetStaticMethodID(static_clazz,function_sub_name,"(II)I");
    //1
    env->CallStaticIntMethod(static_clazz,sub_method_id,12,2);
    return env->NewStringUTF(hello.c_str());
}
