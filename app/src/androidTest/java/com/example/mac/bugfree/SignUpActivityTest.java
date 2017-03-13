package com.example.mac.bugfree;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;
import java.util.Random;

import com.robotium.solo.Solo;

import org.junit.Test;

/**
 * Created by Zhi Li on 2017/2/25.
 */

public class SignUpActivityTest extends ActivityInstrumentationTestCase2<SignUpActivity>{
    private Solo solo;
    private String testName;

    public SignUpActivityTest(){ super(SignUpActivity.class);}

    @Test
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Test
    public void testStart() throws Exception{
        AppCompatActivity activity = getActivity();
    }

    @Test
    // Test if a user is allowed to be sign up with a name already exist in the Elastic Search
    public void testDuplicate(){
        Random rand = new Random();
        boolean Exist = true;
        while(Exist){
            testName = Integer.toString(rand.nextInt(999) + 1);
            ElasticsearchUserController.IsExist isExist = new ElasticsearchUserController.IsExist();
            isExist.execute(testName);
            try {
                Exist = isExist.get();
            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }
        }

        User user_1 = new User(testName);
        ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(user_1);

        //Test duplicates
        Log.i("Duplicate_user_name_is", testName);
        solo.enterText((EditText) solo.getView(R.id.signup_edit), testName);
        solo.clickOnButton("Sign up");

        SystemClock.sleep(3000);
        //check if the user requested exists
        ElasticsearchUserController.IsExist isExist1 = new ElasticsearchUserController.IsExist();
        isExist1.execute(testName);

        try {
            solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }

    }

    @Test
    //Test Sign up function of Sign up UI.
    public void testSignUp(){
        SignUpActivity activity = (SignUpActivity) solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        Random rand = new Random();
        boolean Exist = true;
        while(Exist){
            testName = Integer.toString(rand.nextInt(999) + 1);
            ElasticsearchUserController.IsExist isExist = new ElasticsearchUserController.IsExist();
            isExist.execute(testName);

            try {
                Exist = isExist.get();
            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }
        }
        Log.i("Test_user_name_is", testName);
        solo.enterText((EditText) solo.getView(R.id.signup_edit), testName);
        solo.clickOnButton("Sign up");

        SystemClock.sleep(3000);
        //check if the user requested exists
        ElasticsearchUserController.IsExist isExist = new ElasticsearchUserController.IsExist();
        isExist.execute(testName);

        try {
            assertTrue(isExist.get());
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }
    }
}
