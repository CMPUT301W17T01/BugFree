package com.example.mac.bugfree.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.R;
import com.example.mac.bugfree.controller.ElasticsearchUserListController;
import com.example.mac.bugfree.module.User;
import com.example.mac.bugfree.module.UserNameList;

import java.util.ArrayList;

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

        ImageView imageView = (ImageView) findViewById(R.id.title_image);
        imageView.setImageResource(R.drawable.umood);

        Button signUpButton = (Button) findViewById(R.id.signup_button);
        signUpText = (EditText) findViewById(R.id.signup_edit);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpName = signUpText.getText().toString();
                if (notExist(signUpName) &&!signUpName.equals("")){
                    if (createUser(signUpName) && updateUserNameList(signUpName)) {
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

    private boolean updateUserNameList(String usrName) {
        UserNameList userNameList = new UserNameList();

        ElasticsearchUserListController.GetUserListTask getUserListTask = new ElasticsearchUserListController.GetUserListTask();
        getUserListTask.execute("name");
        try{
            userNameList = getUserListTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
            return false;
        }

        if (userNameList != null) {
            userNameList.addUserName(usrName);
            ElasticsearchUserListController.AddUserListTask addUserListTask = new ElasticsearchUserListController.AddUserListTask();
            addUserListTask.execute(userNameList);
            return true;
        } else {
            ArrayList<String> usrNameList = new ArrayList<String>();
            usrNameList.add(usrName);
            userNameList = new UserNameList(usrNameList);
            ElasticsearchUserListController.AddUserListTask addUserListTask = new ElasticsearchUserListController.AddUserListTask();
            addUserListTask.execute(userNameList);
            return true;
        }
    }
}
