package pengyanb.com.modulo7calculator;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//                    Modulo 7 Calculator                   //
//      + / or - two operands, then result modulo with 7    //
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////

public class CalculatorActivity extends AppCompatActivity {

    //region Private variables
    private Boolean userIsEnteringANumber = false;  //flag indicates if the use is entering a number. It gets set to false when user press operation buttons
    private String calculatorDisplayValue = "0";    //value to be display in the EditText field
    private CalculatorModel calculatorModel = new CalculatorModel();    //Calculator Model
    //endregion



//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//                  View Life Cycle methods                 //
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
    //region View Life Cycle methods
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //prepare user interface base the current orientation
        if((newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) || (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)){
            prepareUserInterface();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //prepare user interface base the current orientation
        prepareUserInterface();
    }
    //endregion


//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//               User Interface creation methods            //
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
    //region User Interface creation methods
    private void prepareUserInterface(){
        final CalculatorActivity thisActivity = this;
        //find the main linearLayout
        final LinearLayout calculatorMainLinearLayout = (LinearLayout) findViewById(R.id.caculator_main_linearlayout);
        //empty all child views
        calculatorMainLinearLayout.removeAllViews();
        //Initiate a EditText for displaying the calculation results
        EditText calculatorDisplayEditText = new EditText(this);
        calculatorDisplayEditText.setTag("calculatorDisplayEditText");
        LinearLayout.LayoutParams calculatorDisplayEditTextLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        calculatorDisplayEditText.setLayoutParams(calculatorDisplayEditTextLayoutParams);
        calculatorDisplayEditText.setHint("Modulo 7 Calculator");                       //set placeholder
        calculatorDisplayEditText.setBackgroundResource(R.drawable.edit_text_style);    //customize style
        calculatorDisplayEditText.setGravity(Gravity.RIGHT);                            //right align text
        calculatorDisplayEditText.setText(calculatorDisplayValue);                      //set current display value
        calculatorDisplayEditText.setFocusable(false);                                  //disable user editing

        calculatorMainLinearLayout.addView(calculatorDisplayEditText);

        //wait for the getMeasuredWdith and getMeasuredHeight been populated with correct values
        calculatorMainLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                calculatorMainLinearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                thisActivity.drawCalculatorUserInterface();     //draw the rest of the user interface
            }
        });
    }

    private void drawCalculatorUserInterface(){
        LinearLayout calculatorMainLinearLayout = (LinearLayout) findViewById(R.id.caculator_main_linearlayout);
        EditText calculatorDisplayEditText = (EditText) calculatorMainLinearLayout.findViewWithTag("calculatorDisplayEditText");
        int calculatorMainLinearLayoutWidth = calculatorMainLinearLayout.getMeasuredWidth() - calculatorMainLinearLayout.getPaddingLeft() - calculatorMainLinearLayout.getPaddingRight();
        int calculatorMainLinearLayoutHeight = calculatorMainLinearLayout.getMeasuredHeight() - calculatorMainLinearLayout.getPaddingTop() - calculatorMainLinearLayout.getPaddingBottom() - calculatorDisplayEditText.getMeasuredHeight();
        int cellWidth = calculatorMainLinearLayoutWidth / 4;        //calculate the button witdh
        int cellHeight = calculatorMainLinearLayoutHeight / 3;      //calculate the button height
        int cellHorizontalMargin = 0;
        if (cellWidth > cellHeight){        //maintain button as squre shape
            cellWidth = cellHeight;
        }
        cellHorizontalMargin = ((calculatorMainLinearLayoutWidth - (cellWidth * 4)) / 8); // calculate the left/right margin between buttons

        //initialize button names array
        List<String> buttonNames = Arrays.asList("1", "2", "3", "x",
                "4", "5", "6", "-",
                "DEL", "0", "=", "+");
        final CalculatorActivity thisActivity = this;
        //Button onClick listener
        OnClickListener calculatorButtonOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                Button buttonPressed = (Button) view;
                thisActivity.handleCalculatorButtonPressed(buttonPressed); //handle calculated button pressed events
            }
        };

        for(int i=0; i<3; i++){ //for each row, create a horizontal LinearLayout container
            LinearLayout rowLinearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams rowLinearLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            rowLinearLayout.setLayoutParams(rowLinearLayoutParams);
            rowLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            calculatorMainLinearLayout.addView(rowLinearLayout);

            for(int j=0; j<4; j++){ //for each column, create each button
                Button calculatorButton = new Button(this);
                LinearLayout.LayoutParams calculatorButtonLayoutParams = new LinearLayout.LayoutParams(cellWidth, cellWidth);
                calculatorButtonLayoutParams.setMargins(cellHorizontalMargin, 0, cellHorizontalMargin, 0);
                calculatorButton.setLayoutParams(calculatorButtonLayoutParams);
                calculatorButton.setText(buttonNames.get(i * 4 + j));                   //get the button name from nameArray
                calculatorButton.setOnClickListener(calculatorButtonOnClickListener);   //add onClick listener
                calculatorButton.setBackgroundResource(android.R.drawable.btn_default);
                rowLinearLayout.addView(calculatorButton);
            }
        }
    }
    //endregion


//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//         Handle button click and interact with model      //
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
    //region Handle button click and interact with model
    private void handleCalculatorButtonPressed(Button buttonPressed){ //Calculator button click handler
        LinearLayout calculatorMainLinearLayout = (LinearLayout) findViewById(R.id.caculator_main_linearlayout);
        EditText calculatorDisplayEditText = (EditText) calculatorMainLinearLayout.findViewWithTag("calculatorDisplayEditText");
        String currentDisplay = calculatorDisplayEditText.getText().toString();

        ViewGroup viewGroup = (ViewGroup) calculatorMainLinearLayout;
        for(int i = 0; i < viewGroup.getChildCount(); i++){     //to find each button, and clear its highlight
            View child = viewGroup.getChildAt(i);
            if(child instanceof LinearLayout){                  //find row container
                ViewGroup rowViewGroup = (ViewGroup) child;
                for(int j=0; j<rowViewGroup.getChildCount(); j++){
                    View btn = rowViewGroup.getChildAt(j);
                    if(btn instanceof Button){                  //find each button within the row
                        Button childButton = (Button) btn;
                        childButton.setBackgroundResource(android.R.drawable.btn_default);
                        childButton.setTextColor(getResources().getColor(R.color.colorBlack));
                    }
                }
            }
        }

        switch (buttonPressed.getText().toString()){
            case "+":   //handle plus button pressed
                handleOperation(buttonPressed);
                break;
            case "-":   //handle minus button pressed
                //System.out.println("DisplayValue: "+currentDisplay);
                if(currentDisplay.equals("0")){          //user entered a minus sign as the first letter
                    userIsEnteringANumber = true;        //set flag indicate, user start entering a number
                    calculatorDisplayEditText.setText("-"); //display minus sign
                    calculatorDisplayValue = "-";
                }
                else {
                    handleOperation(buttonPressed);    //handle minus operation
                }
                break;
            case "x":   //handle multiply operation
                handleOperation(buttonPressed);
                break;
            case "=":   //calculate the result
                try{
                    Integer operand = Integer.parseInt(currentDisplay);
                    calculatorModel.apiPushOprandToStack(operand);      //push the currently displayed value onto operand stack
                    if(calculatorModel.apiPerformCalculation()){        //if model calculation result is valid
                        calculatorDisplayEditText.setText(""+calculatorModel.getCalculatedResult());    //display result
                        calculatorDisplayValue = calculatorDisplayEditText.getText().toString();
                        Toast.makeText(this, calculatorModel.getResultDiscription(), Toast.LENGTH_SHORT).show();    //show formula
                    }
                    userIsEnteringANumber = false;      //unset flag
                }
                catch (Exception e){
                    Log.i("Not a valid number yet","User input is not valid yet.");
                }
                break;
            case "DEL":     //Delete button pressed
                if(currentDisplay.length() > 0) {
                    String newDisplayValue = currentDisplay.substring(0, currentDisplay.length() - 1); //remove the last letter
                    if(newDisplayValue.length() == 0){
                        newDisplayValue = "0";          //display 0 when value get deleted completely
                    }
                    calculatorDisplayEditText.setText(newDisplayValue);
                    calculatorDisplayValue = calculatorDisplayEditText.getText().toString();
                }
                break;
            default:        //Number button pressed
                //System.out.println("user Entering: " + userIsEnteringANumber);
                if(!userIsEnteringANumber){     //if user is not entering number before this
                    userIsEnteringANumber = true;  //set the flag
                    calculatorDisplayEditText.setText(buttonPressed.getText().toString());  //display new value
                }
                else{                           //else user is in the middle of entering a number
                    calculatorDisplayEditText.setText(currentDisplay + buttonPressed.getText().toString());  //append the value
                    try{
                        Integer parsedValue = Integer.parseInt(calculatorDisplayEditText.getText().toString());
                        calculatorDisplayEditText.setText(""+parsedValue);
                    }
                    catch (Exception e){
                        Log.i("Not a valid number yet","User input is not valid yet.");
                    }
                }
                calculatorDisplayValue = calculatorDisplayEditText.getText().toString();
                break;
        }
    }
    //interact with model and calculate the result
    private void performModulo7Calculation(){
        LinearLayout calculatorMainLinearLayout = (LinearLayout) findViewById(R.id.caculator_main_linearlayout);
        EditText calculatorDisplayEditText = (EditText) calculatorMainLinearLayout.findViewWithTag("calculatorDisplayEditText");

        if (calculatorModel.apiPerformCalculation()){  //display result if a valid result calculated
            calculatorDisplayEditText.setText(""+calculatorModel.getCalculatedResult());
            calculatorDisplayValue = calculatorDisplayEditText.getText().toString();
            Toast.makeText(this, calculatorModel.getResultDiscription(), Toast.LENGTH_SHORT).show();
        }
        userIsEnteringANumber = false; //clear user entering flag
    }

    //handle + - x operations
    private void handleOperation(Button buttonPressed){

        LinearLayout calculatorMainLinearLayout = (LinearLayout) findViewById(R.id.caculator_main_linearlayout);
        EditText calculatorDisplayEditText = (EditText) calculatorMainLinearLayout.findViewWithTag("calculatorDisplayEditText");
        String currentDisplay = calculatorDisplayEditText.getText().toString();

        try{
            Integer operand = Integer.parseInt(currentDisplay);  //a valid number is entered
            userIsEnteringANumber = false;      //set flag indicates that user is not in the middle of entering a number
            calculatorModel.apiPushOprandToStack(operand);      //push the currently displayed value onto the operand stack

            if(calculatorModel.getOperandStack().size() == 1){       //if only one operand is pushed to the stack previously
                buttonPressed.setTextColor(getResources().getColor(R.color.colorWhite));
                buttonPressed.setBackgroundColor(getResources().getColor(R.color.colorDarkGray)); //high light the button pressed
            }
            else{
                if(calculatorModel.apiPerformCalculation()) {       //two+ operand in the operand stack, calculate the result
                    calculatorDisplayEditText.setText("" + calculatorModel.getCalculatedResult());
                    calculatorDisplayValue = calculatorDisplayEditText.getText().toString();
                    Toast.makeText(this, calculatorModel.getResultDiscription(), Toast.LENGTH_SHORT).show();
                }
                userIsEnteringANumber = false;
            }
            //set the model's operationSymbol
            calculatorModel.apiSetOperationSymbol(buttonPressed.getText().toString());
            //operationSymbol = buttonPressed.getText().toString();
        }
        catch (Exception e){
            Log.i("Not a valid number yet","User input is not valid yet.");
        }
    }
    //endregion

//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//                   menu related functions                 //
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_calculator, menu);   //hide menu button
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
