package com.example.mac.bugfree;

import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.robotium.solo.Solo;

import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Zhi Li on 2017/2/25.
 */

public class SignInActivityTest extends ActivityInstrumentationTestCase2<SignInActivity> {
    private Solo solo;
    private String testName;

    public SignInActivityTest(){ super(SignInActivity.class);}

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception{
        AppCompatActivity activity = getActivity();
    }

    public void testSignIn(){
        SignInActivity activity = (SignInActivity) solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", SignInActivity.class);

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

        SystemClock.sleep(3000);
        //check if the user requested exists
        ElasticsearchUserController.IsExist isExist = new ElasticsearchUserController.IsExist();
        isExist.execute(testName);

        try {
            assertTrue(isExist.get());
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }
        solo.enterText((EditText) solo.getView(R.id.login_edit), testName);
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

    }

    public void testRegister(){
        solo.clickOnButton("Create Account");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
    }

}
