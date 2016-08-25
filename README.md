# Modulo7Calculator
android modulo 7 calculator

### Project Structure

```
+--app
    |+--src
    |     |+--androidTest [Instrumentation test]
    |     |        |+--ApplicationTest
    |     |        |+--CalculatorActivityTest [UX Automation test]
    |     |+--main
    |     |        |+--java  [Java package]
    |     |             |+--pengyanb.com.modulo7calculator
    |     |             |     |+--CalculatorActivity  [main activity]
    |     |             |     |+--CalculatorModel     [Model abstraction]
    |     |             |+jniLibs [compiled native c module]   
    |     |             |+res [resource folders]
    |     |+--test
    |           |+--CalculatorModelUnitTest [Unit Test]
    |+--jni [Native C folder]
        |+--ndkmodulo7.c [modulo 7 calculation]  
        |+--Android.mk
        |+--Application.mk
