package pengyanb.com.modulo7calculator;

import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by Peng on 25/08/16.
 */

public class CalculatorModelUnitTest extends TestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @SmallTest
    public void testModulo7Calculation(){
        System.setProperty("java.library.path", "src/main/jniLibs");
        CalculatorModel calculatorModel = new CalculatorModel(); //initiate calculator model
        String[] operations  = new String[]{"+", "-", "x"};     //available operations

        Random seed = new Random();                             //random number generator

        String operation = "";
        int operand1 = 0;
        int operand2 = 0;
        int result = 0;
        boolean isValid = false;

        do {
            operation = operations[seed.nextInt(3)];            //pick a random operation
            operand1 = seed.nextInt();                          //generate random operands
            operand2 = seed.nextInt();
            try{                                    //operation may cause overflow. if it happens, re-enter this procedure
                if("+".equals(operation)){
                    result = (operand1 + operand2) % 7;
                    isValid = true;
                }
                else if("-".equals(operation)){
                    result = (operand1 - operand2) % 7;
                    isValid = true;
                }
                else if("x".equals(operation)){
                    result = (operand1 * operand2) % 7;
                    isValid = true;
                }
            }
            catch (Exception e){
                Log.i("ModelUnitTest", "Overflow occurred");
            }
        }while (!isValid);                          //a valid value is calculated

        calculatorModel.apiPushOprandToStack(operand1);     //push the same operands on the calculator model's operand stack
        calculatorModel.apiPushOprandToStack(operand2);
        calculatorModel.apiSetOperationSymbol(operation);   //set the same operation

        if(calculatorModel.apiPerformCalculation()){        //two result should equal
            assertEquals("Calculator Model - incorrect result: " + result + " = " + calculatorModel.getCalculatedResult(), result, calculatorModel.getCalculatedResult());
        }
        else{
            Assert.assertTrue("Calculator Model - no result:",false);
        }

    }
}
