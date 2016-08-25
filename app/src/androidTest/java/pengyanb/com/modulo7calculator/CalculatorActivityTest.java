package pengyanb.com.modulo7calculator;

import android.test.ActivityInstrumentationTestCase2;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Peng on 25/08/16.
 */
public class CalculatorActivityTest extends ActivityInstrumentationTestCase2<CalculatorActivity> {
    public CalculatorActivityTest(Class<CalculatorActivity> activityClass) {
        super(activityClass);
    }

    @Test
    public void testChildViewCount(){ //test right amount of child UI is added to the main LinearLayout containner
        LinearLayout mainLayout = (LinearLayout) getActivity().findViewById(R.id.caculator_main_linearlayout);
        ViewGroup viewGroup = (ViewGroup) mainLayout;
        int childCount = viewGroup.getChildCount(); //childCount = 4 (EditText + 3 rows)
        Assert.assertEquals("Child Count matches", childCount, 4);
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
