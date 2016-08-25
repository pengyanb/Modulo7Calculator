package pengyanb.com.modulo7calculator;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Peng on 25/08/16.
 */
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//                   Modulo 7 Calculator Model              //
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
public class CalculatorModel {
    //region Variables/Setters/Getters
    public ArrayList<Integer> getOperandStack() {
        return operandStack;
    } //internal operand stack getter
    private ArrayList<Integer> operandStack; //internal operand stack

    private String operationSymbol; //next operation

    public String getResultDiscription() {
        return resultDiscription;
    } //result discription, shows the formula

    private String resultDiscription = "";

    public int getCalculatedResult() {
        return calculatedResult;
    }
    private int calculatedResult = 0; //calculation result
    //endregion

    //region NDK Library related
    public native int executeModulo7Calculation(int[] operands, String operationSymbol);

    static { //Load Native C Library
        System.loadLibrary("ndkmodulo7");
    }
    //endregion

    //region Constructor
    public CalculatorModel(){
        operandStack = new ArrayList<Integer>();
    }
    //endregion


    //region Public API
    public void apiPushOprandToStack(Integer operand){
        operandStack.add(operand);
    } //push operand on the internal stack

    //set next operation
    public void apiSetOperationSymbol(String operationSymbol) {
        this.operationSymbol = operationSymbol;
    }
    //start calculation with current operand stack and operation symbol
    public boolean apiPerformCalculation(){
        if((operandStack.size() > 1) && (!"".equals(operationSymbol)) ){ //if more than two operands pushed onto the stack and operation is defined
            int[] stack = new int[operandStack.size()];
            for(int i=0; i<operandStack.size(); i++){
                stack[i] = operandStack.get(i).intValue();
            }
            calculatedResult = executeModulo7Calculation(stack, operationSymbol);
            Log.i("modulo7Calculation",  "(" +
                    operandStack.get(0) + " " +
                    operationSymbol + " " +
                    operandStack.get(1) + ") % 7 = " + calculatedResult);
            resultDiscription =  "(" +
                    operandStack.get(0) + " " +
                    operationSymbol + " " +
                    operandStack.get(1) + ") % 7 = " + calculatedResult;  //set result description formula
            operandStack.clear();
            operationSymbol = "";
            return true;
        }
        return false;
    }
    //endregion
}
