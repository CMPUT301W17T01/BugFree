package com.example.mac.bugfree;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.example.mac.bugfree.activity.MainActivity;
import com.example.mac.bugfree.activity.SignInActivity;
import com.example.mac.bugfree.activity.SignUpActivity;
import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.module.User;
import com.robotium.solo.Solo;

import org.junit.Test;

import java.util.Random;

/**
 * After running this test, if there are more test to do, please open the app in Emulator
 * and sign out and resignin as John. Other wise other test will fail.
 * Created by Zhi Li on 2017/2/25.
 */

public class SignInActivityTest extends ActivityInstrumentationTestCase2<SignInActivity> {
    private Solo solo;
    private String testName;

    public SignInActivityTest(){ super(SignInActivity.class);}

    @Test
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Test
    public void testStart() throws Exception{
        AppCompatActivity activity = getActivity();
    }

    @Test
    //Test if the user can enter main page after login using a valid user name
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

    @Test
    //Test if user can jump to SignUp page when user click the Create Account button
    public void testRegister(){
        solo.clickOnButton("Create Account");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
    }

}
