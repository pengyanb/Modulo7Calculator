#include<jni.h>
#include<string.h>
#include<android/log.h>

jint Java_pengyanb_com_modulo7calculator_CalculatorModel_executeModulo7Calculation(JNIEnv *env, jobject sender, jintArray operands, jstring operationSymbol){
    //jintArray* operandArray = (jintArray*) (&operands);
    jint *operandArray = (*env)->GetIntArrayElements(env, operands, NULL);
    __android_log_print(ANDROID_LOG_INFO, "ndkmodulo7", "operand1 = %d", operandArray[0]);
    __android_log_print(ANDROID_LOG_INFO, "ndkmodulo7", "operand1 = %d", operandArray[1]);

    const char *nativeOperationSymbol = (*env)->GetStringUTFChars(env, operationSymbol, 0);
    //__android_log_print(ANDROID_LOG_INFO, "ndkmodulo7", "operation = %s", nativeOperationSymbol);

    if(strcmp("+", nativeOperationSymbol) == 0){
        __android_log_print(ANDROID_LOG_INFO, "ndkmodulo7", "operationSymbol = +" );
        (*env)->ReleaseIntArrayElements(env, operands, operandArray, 0);
        (*env)->ReleaseStringUTFChars(env, operationSymbol, nativeOperationSymbol);
        return (operandArray[0] + operandArray[1]) % 7 ;
    }
    else if(strcmp("-", nativeOperationSymbol) == 0){
        __android_log_print(ANDROID_LOG_INFO, "ndkmodulo7", "operationSymbol = -" );
        (*env)->ReleaseIntArrayElements(env, operands, operandArray, 0);
        (*env)->ReleaseStringUTFChars(env, operationSymbol, nativeOperationSymbol);
        return (operandArray[0] - operandArray[1]) % 7 ;
    }
    else if(strcmp("x", nativeOperationSymbol) == 0){
        __android_log_print(ANDROID_LOG_INFO, "ndkmodulo7", "operationSymbol = x" );
        (*env)->ReleaseIntArrayElements(env, operands, operandArray, 0);
        (*env)->ReleaseStringUTFChars(env, operationSymbol, nativeOperationSymbol);
        return (operandArray[0] * operandArray[1]) % 7 ;
    }
    else{
        __android_log_print(ANDROID_LOG_INFO, "ndkmodulo7", "operationSymbol = else" );
        (*env)->ReleaseIntArrayElements(env, operands, operandArray, 0);
        (*env)->ReleaseStringUTFChars(env, operationSymbol, nativeOperationSymbol);
        return 0 ;
    }

    //(*env)->ReleaseIntArrayElements(env, operands, operandArray, 0);
    //(*env)->ReleaseStringUTFChars(env, operationSymbol, nativeOperationSymbol);

}