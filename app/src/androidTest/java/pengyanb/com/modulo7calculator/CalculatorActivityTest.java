package pengyanb.com.modulo7calculator;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Peng on 25/08/16.
 */
public class CalculatorActivityTest extends ActivityInstrumentationTestCase2<CalculatorActivity> {

    public CalculatorActivityTest(){
        super(CalculatorActivity.class);
    }

    @Test
    public void testChildViewCount(){ //test right amount of child UI is added to the main LinearLayout containner
        LinearLayout mainLayout = (LinearLayout) getActivity().findViewById(R.id.caculator_main_linearlayout);
        ViewGroup viewGroup = (ViewGroup) mainLayout;
        int childCount = viewGroup.getChildCount(); //childCount = 4 (EditText + 3 rows)
        Assert.assertEquals("Number of rows in main LinearLayout must matches 4", childCount, 4);

        for(int i=0; i<viewGroup.getChildCount(); i++){
            View child = viewGroup.getChildAt(i);
            if(child instanceof  LinearLayout){  //if child is a row LinearLayout
                ViewGroup rowViewGroup = (ViewGroup) child;
                int buttonCount = rowViewGroup.getChildCount();  //get button count in each row
                assertEquals("Buttons count in each row must muches 4", buttonCount, 4);
            }
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
