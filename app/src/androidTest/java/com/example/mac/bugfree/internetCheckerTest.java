package com.example.mac.bugfree;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import com.example.mac.bugfree.util.InternetConnectionChecker;


/**
 * Created by Zhi Li on 2017/3/23.
 */

public class internetCheckerTest extends ActivityInstrumentationTestCase2 {
    public internetCheckerTest(){ super(InternetConnectionChecker.class);}

    public void testInternetChecker(){
        //will pass when internet is available
        InternetConnectionChecker checker = new InternetConnectionChecker();
        Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
        assertTrue(checker.isOnline(context));
    }

}
