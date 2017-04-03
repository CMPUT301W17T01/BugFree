package com.example.mac.bugfree;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.example.mac.bugfree.activity.BlockListActivity;
import com.example.mac.bugfree.activity.FriendActivity;
import com.robotium.solo.Solo;

/**
 * Created by yipengzhou on 2017/4/2.
 */

public class BlockListActivityUnitTest extends ActivityInstrumentationTestCase2<BlockListActivity> {
    private Solo solo;


    public BlockListActivityUnitTest(){
        super(BlockListActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testHomeBtn(){
        View menu = solo.getView(R.id.homeBtn);
        solo.assertCurrentActivity("Wrong Activity", BlockListActivity.class);
        solo.clickOnView(menu);
        solo.assertCurrentActivity("Wrong Activity", BlockListActivity.class);
    }

}
