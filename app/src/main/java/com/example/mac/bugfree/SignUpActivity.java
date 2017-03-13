package com.example.mac.bugfree;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This is the Sign In view class of the project, <br> In this class, user interaction and Elastic Query action are performed
 * <p>User input of user name is check by Elastic search, no duplicate is allowed.
 * User will back to Signin screen when Sign up is finished</p>
 *
 * @author Zhi Li
 * @version 2.0
 * @see SignInActivity
 */
public class SignUpActivity extends AppCompatActivity {
    /**
     * The Sign up text.
     */
    protected EditText signUpText;
    private String signUpName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_signup);
        setSupportActionBar(toolbar);

        Button signUpButton = (Button) findViewById(R.id.signup_button);
        signUpText = (EditText) findViewById(R.id.signup_edit);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpName = signUpText.getText().toString();
                if (notExist(signUpName) &&!signUpName.equals("")){
                    if (createUser(signUpName)) {
                        Toast.makeText(getApplicationContext(),
                                "User " + signUpName + " created.",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "Unknown error. User " + signUpName + " not created.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * This method validates if the user input of user name exist in Elastic search.
     * @param newName String of user name needed to be validate
     * @return boolean of if the user does not exist in Elastic search
     */
    private boolean notExist(String newName){
        ElasticsearchUserController.IsExist isExist = new ElasticsearchUserController.IsExist();
        isExist.execute(newName);
        try {
            if(!isExist.get()){
                return true;
            } else{
                Toast.makeText(getApplicationContext(),
                        "Not created, "+signUpName+" already exists.",
                        Toast.LENGTH_SHORT).show();
                return false;}
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
            Toast.makeText(getApplicationContext(),
                    "Can not verify uniqueness. Internet connection Error",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * This method creates a user in Elastic Search
     * @param usr String of user name needed to be created.
     * @return boolean of if user created online
     */
    private boolean createUser(String usr){
        try {
            User user = new User(usr);
            ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
            addUserTask.execute(user);
            return true;
        }catch (Exception e){
            Log.i("Error", "Failed to create the User");
            Toast.makeText(getApplicationContext(),
                    "Can not create user. Internet connection Error",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
