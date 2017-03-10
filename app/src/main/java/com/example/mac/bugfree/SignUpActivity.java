package com.example.mac.bugfree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    protected EditText signUpText;
    private String signUpName;
    private UserList userList= new UserList();
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

    //Check duplicate user name
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

    //create a user using elastic search
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
